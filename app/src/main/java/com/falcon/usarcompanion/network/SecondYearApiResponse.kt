package com.falcon.usarcompanion.network

import com.squareup.moshi.Json
import java.io.Serializable

class SecondYearApiResponse (
    @Json(name = "subjects") val subjects: List<Subject>
): Serializable
