package com.falcon.usarcompanion.network
import com.squareup.moshi.Json
import java.io.Serializable

data class FirstYearApiResponse (
    //val subjects: List<Subject>
    @Json(name = "subjects") val subjects: List<Subject>
): Serializable

