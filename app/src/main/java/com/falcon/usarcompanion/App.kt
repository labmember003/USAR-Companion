package com.falcon.usarcompanion

import android.app.Application
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.falcon.usarcompanion.network.Network
import java.io.InputStream

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        // Load images (subject icons, all on GitHub) through the same client as the data,
        // so they also use DNS-over-HTTPS and the jsDelivr fallback on mobile data.
        Glide.get(this).registry.replace(
            GlideUrl::class.java,
            InputStream::class.java,
            OkHttpUrlLoader.Factory(Network.client)
        )
    }
}
