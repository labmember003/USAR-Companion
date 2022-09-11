package com.falcon.usarcompanion.network

import com.squareup.moshi.Json
import java.io.Serializable

data class Branch (
    @Json(name = "branch_Name") val branchName: String,
    @Json(name = "subjects") val subjects: List<Subject>,
): Serializable
