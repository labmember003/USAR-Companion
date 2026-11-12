package com.falcon.usarcompanion.network

import okhttp3.Dns
import okhttp3.OkHttpClient
import okhttp3.Request
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File
import java.net.InetAddress
import java.net.UnknownHostException
import java.nio.file.Files

/**
 * Simulates a mobile network that blocks GitHub at the DNS level (the "doesn't work on
 * mobile data" reviews). These tests hit the real GitHub, Cloudflare/Google DNS and jsDelivr.
 */
class NetworkTest {

    private val dataUrl = "https://github.com/labmember003/usar_data/raw/master/YEAR_1/data.json"

    /** A carrier DNS that refuses GitHub hosts and resolves everything else normally. */
    private val carrierBlockingGithub = object : Dns {
        override fun lookup(hostname: String): List<InetAddress> {
            if (hostname.contains("github")) throw UnknownHostException("blocked by carrier: $hostname")
            return Dns.SYSTEM.lookup(hostname)
        }
    }

    private fun fetch(client: OkHttpClient, url: String): Pair<Int, String> =
        client.newCall(Request.Builder().url(url).build()).execute().use { it.code() to (it.body()?.string() ?: "") }

    @Test
    fun mapsGithubRawUrlsToJsDelivr() {
        assertEquals(
            "https://cdn.jsdelivr.net/gh/labmember003/usar_data@master/YEAR_1/data.json",
            Network.jsDelivrUrlFor(dataUrl)
        )
        assertEquals(
            "https://cdn.jsdelivr.net/gh/labmember003/usar_data@master/YEAR_1/icons/notes.png",
            Network.jsDelivrUrlFor("https://raw.githubusercontent.com/labmember003/usar_data/master/YEAR_1/icons/notes.png")
        )
        assertNull(Network.jsDelivrUrlFor("https://www.youtube.com/watch?v=abc"))
    }

    @Test
    fun withoutFixBlockedCarrierDnsFails() {
        val plain = OkHttpClient.Builder().dns(carrierBlockingGithub).build()
        val failed = try {
            fetch(plain, dataUrl); false
        } catch (e: UnknownHostException) {
            true
        }
        assertTrue("a plain client should fail when the carrier blocks GitHub", failed)
    }

    @Test
    fun dnsOverHttpsLoadsDataWhenCarrierDnsBlocksGithub() {
        val client = Network.buildClient(systemDns = carrierBlockingGithub)
        val (code, body) = fetch(client, dataUrl)
        assertEquals(200, code)
        assertTrue(body.contains("\"subjects\""))
    }

    @Test
    fun jsDelivrFallbackWhenGithubCompletelyUnreachable() {
        // No DNS-over-HTTPS here: GitHub is unreachable however it is resolved.
        val client = OkHttpClient.Builder()
            .dns(carrierBlockingGithub)
            .addInterceptor(Network.GithubMirrorInterceptor())
            .build()
        val (code, body) = fetch(client, dataUrl)
        assertEquals(200, code)
        assertTrue(body.contains("\"subjects\""))
    }

    @Test
    fun downloadWritesCompleteFileAndNoPartial() {
        val dir = Files.createTempDirectory("usar").toFile()
        val target = File(dir, "MinorExam.pdf")
        var lastProgress = -2
        Network.download(
            "https://github.com/labmember003/usar_data/raw/master/YEAR_1/Sem1/CommunicationSkills/Exam/MinorExam.pdf",
            target,
            isCancelled = { false }
        ) { lastProgress = it }
        assertTrue(target.length() > 1000)
        assertEquals("%PDF", target.readBytes().copyOfRange(0, 4).toString(Charsets.US_ASCII))
        assertFalse(File(dir, "MinorExam.pdf.part").exists())
        assertEquals(100, lastProgress)
        dir.deleteRecursively()
    }
}
