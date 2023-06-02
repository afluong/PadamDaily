package io.padam_exercise.padamdaily.network

import com.squareup.moshi.Json
import java.util.concurrent.TimeUnit
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit


data class Leg(
    @Json(name = "steps")
    val steps: List<Step>?
) {
    fun getDuration(): Duration {
        var total = 0L
        steps?.forEach { step ->
            total += step.duration?.valueInSecond ?: 0
        }
        return total.seconds
    }
}