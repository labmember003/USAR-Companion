package com.falcon.usarcompanion.network

import com.falcon.usarcompanion.network.Section
import com.squareup.moshi.Json

data class Subject (
    @Json(name = "sub_name") val subjectName: String,
    @Json(name = "semester") val semester: String,
    @Json(name = "icon_url") val iconUrl: String,
    @Json(name = "sections") val sections: List<Section>
)


