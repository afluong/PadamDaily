package io.padam_exercise.padamdaily.network

import com.squareup.moshi.Json

data class Location(
    @Json(name = "lat")
    val latitude: Double,

    @Json(name = "lng")
    val longitude: Double
)
