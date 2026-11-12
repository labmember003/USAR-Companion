package com.falcon.usarcompanion.network

import android.util.Log
import okhttp3.Dns
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.dnsoverhttps.DnsOverHttps
import java.io.File
import java.io.IOException
import java.net.InetAddress
import java.net.UnknownHostException
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

/**
 * Networking shared by Retrofit, Glide and file downloads.
 *
 * All app content lives on GitHub (github.com/.../raw/... redirects to raw.githubusercontent.com).
 * Some Indian mobile networks block raw.githubusercontent.com at the DNS level, so the app loaded
 * nothing on mobile data while working on Wi-Fi ("doesn't work on mobile data" reviews).
 *
 * Fix:
 *  1. Resolve hostnames with DNS-over-HTTPS (Cloudflare, then Google) before the carrier's DNS.
 *  2. If GitHub still can't be reached, retry the same file through the jsDelivr CDN mirror
 *     (works for files up to 20 MB, which covers the data, icons and most PDFs).
 */
object Network {
    private const val TAG = "UsarNetwork"

    private val bootstrapClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .build()

    // Literal IPs, so building these never needs a DNS lookup itself.
    private val cloudflareDoh: Dns = DnsOverHttps.Builder()
        .client(bootstrapClient)
        .url(HttpUrl.get("https://cloudflare-dns.com/dns-query"))
        .bootstrapDnsHosts(InetAddress.getByName("1.1.1.1"), InetAddress.getByName("1.0.0.1"))
        .includeIPv6(false) // OkHttp 3 tries addresses in order; IPv4 avoids slow IPv6 timeouts
        .build()

    private val googleDoh: Dns = DnsOverHttps.Builder()
        .client(bootstrapClient)
        .url(HttpUrl.get("https://dns.google/dns-query"))
        .bootstrapDnsHosts(InetAddress.getByName("8.8.8.8"), InetAddress.getByName("8.8.4.4"))
        .includeIPv6(false)
        .build()

    /** Builds the app client. [systemDns] is replaceable so tests can simulate a blocking carrier. */
    fun buildClient(systemDns: Dns = Dns.SYSTEM): OkHttpClient = OkHttpClient.Builder()
        .dns(FallbackDns(listOf(cloudflareDoh, googleDoh, systemDns)))
        .addInterceptor(GithubMirrorInterceptor())
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    val client: OkHttpClient by lazy { buildClient() }

    /**
     * Downloads [url] into [target] (written to a temporary file first, so a half-finished
     * download never looks complete). [onProgress] gets 0..100, or -1 when the size is unknown.
     * [isCancelled] is checked between chunks.
     */
    @Throws(IOException::class)
    fun download(url: String, target: File, isCancelled: () -> Boolean, onProgress: (Int) -> Unit) {
        val partial = File(target.parentFile, target.name + ".part")
        try {
            client.newCall(Request.Builder().url(url).build()).execute().use { response ->
                if (!response.isSuccessful) throw IOException("HTTP ${response.code()} for $url")
                val body = response.body() ?: throw IOException("Empty response for $url")
                val total = body.contentLength()
                var done = 0L
                var lastPercent = -2
                body.byteStream().use { input ->
                    partial.outputStream().use { output ->
                        val buffer = ByteArray(64 * 1024)
                        while (true) {
                            if (isCancelled()) throw IOException("Cancelled")
                            val read = input.read(buffer)
                            if (read < 0) break
                            output.write(buffer, 0, read)
                            done += read
                            val percent = if (total > 0) (done * 100 / total).toInt() else -1
                            if (percent != lastPercent) {
                                lastPercent = percent
                                onProgress(percent)
                            }
                        }
                    }
                }
            }
            if (target.exists()) target.delete()
            if (!partial.renameTo(target)) throw IOException("Could not save ${target.name}")
        } finally {
            if (partial.exists()) partial.delete()
        }
    }

    /** Tries each resolver in order; caches answers for a few minutes. */
    class FallbackDns(private val resolvers: List<Dns>) : Dns {
        private val cache = ConcurrentHashMap<String, Pair<Long, List<InetAddress>>>()

        override fun lookup(hostname: String): List<InetAddress> {
            val now = System.currentTimeMillis()
            cache[hostname]?.let { (time, addresses) ->
                if (now - time < CACHE_MS) return addresses
            }
            var lastError: Exception? = null
            for (resolver in resolvers) {
                try {
                    val addresses = resolver.lookup(hostname)
                    if (addresses.isNotEmpty() && addresses.none { it.isAnyLocalAddress || it.isLoopbackAddress }) {
                        cache[hostname] = now to addresses
                        return addresses
                    }
                } catch (e: Exception) {
                    lastError = e
                    Log.w(TAG, "DNS lookup for $hostname failed with one resolver: ${e.message}")
                }
            }
            throw UnknownHostException("Could not resolve $hostname").apply { lastError?.let { initCause(it) } }
        }

        private companion object {
            const val CACHE_MS = 10 * 60 * 1000L
        }
    }

    /** Retries GitHub raw files through jsDelivr when GitHub fails. */
    class GithubMirrorInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val mirror = jsDelivrUrlFor(request.url().toString()) ?: return chain.proceed(request)
            val response = try {
                chain.proceed(request)
            } catch (e: IOException) {
                Log.w(TAG, "GitHub unreachable (${e.message}), trying mirror")
                return chain.proceed(request.newBuilder().url(mirror).build())
            }
            if (response.isSuccessful || response.code() == 404) return response
            response.close()
            Log.w(TAG, "GitHub returned ${response.code()}, trying mirror")
            return chain.proceed(request.newBuilder().url(mirror).build())
        }
    }

    private val githubRaw = Regex("^https?://github\\.com/([^/]+)/([^/]+)/raw/([^/]+)/(.+)$")
    private val rawGithubusercontent = Regex("^https?://raw\\.githubusercontent\\.com/([^/]+)/([^/]+)/([^/]+)/(.+)$")

    /** Maps a GitHub raw file URL to the same file on jsDelivr, or null for any other URL. */
    fun jsDelivrUrlFor(url: String): String? {
        val match = githubRaw.matchEntire(url) ?: rawGithubusercontent.matchEntire(url) ?: return null
        val (owner, repo, ref, path) = match.destructured
        return "https://cdn.jsdelivr.net/gh/$owner/$repo@$ref/$path"
    }
}
