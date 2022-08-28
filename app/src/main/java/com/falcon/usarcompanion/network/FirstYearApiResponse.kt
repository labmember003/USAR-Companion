package com.falcon.usarcompanion.network
import com.squareup.moshi.Json

data class FirstYearApiResponse (
    //val subjects: List<Subject>
    @Json(name = "subjects") val subjects: List<Subject>
)

