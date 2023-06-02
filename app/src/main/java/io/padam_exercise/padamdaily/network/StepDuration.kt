package io.padam_exercise.padamdaily.network

import com.squareup.moshi.Json

data class StepDuration(

    @Json(name = "value")
    val valueInSecond: Long

)
