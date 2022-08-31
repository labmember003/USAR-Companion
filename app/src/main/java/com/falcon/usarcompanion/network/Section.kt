package com.falcon.usarcompanion.network

import com.falcon.usarcompanion.network.Content
import com.squareup.moshi.Json
import java.io.Serializable

data class Section (
    @Json(name = "title") val title: String,
    @Json(name = "icon_url") val iconSrc: String,
    @Json(name = "contents") val contents: List<Content>
): Serializable

