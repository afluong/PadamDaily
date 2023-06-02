package io.padam_exercise.padamdaily.network

import com.squareup.moshi.Json


data class Step(
    @Json(name = "start_location")
    val startLocation: Location?,

    @Json(name = "end_location")
    val endLocation: Location?,

    @Json(name = "duration")
    val duration: StepDuration?
)