package io.padam_exercise.padamdaily.network

import com.squareup.moshi.Json


data class Leg(
    @Json(name = "steps")
    val steps: List<Step>?
)