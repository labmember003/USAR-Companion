package com.falcon.usarcompanion.network

import com.squareup.moshi.Json

data class Content (
    @Json(name = "source_url") val sourceUrl: String,
    @Json(name = "name") val name: String
)
