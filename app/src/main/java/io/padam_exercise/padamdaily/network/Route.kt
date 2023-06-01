package io.padam_exercise.padamdaily.network

import com.squareup.moshi.Json


data class Route(
    @Json(name = "legs")
    val legs: List<Leg>?
)