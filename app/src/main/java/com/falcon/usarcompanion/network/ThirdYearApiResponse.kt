package com.falcon.usarcompanion.network

import com.squareup.moshi.Json
import java.io.Serializable

class ThirdYearApiResponse (
    @Json(name = "branches") val branches: List<Branch>
): Serializable
