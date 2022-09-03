package com.falcon.usarcompanion.network

import com.squareup.moshi.Json
import java.io.Serializable

class ThirdYearApiResponse (
    @Json(name = "subjects") val subjects: List<Subject>
): Serializable
