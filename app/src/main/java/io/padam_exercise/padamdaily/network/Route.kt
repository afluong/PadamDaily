package io.padam_exercise.padamdaily.network

import com.squareup.moshi.Json
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds


data class Route(
    @Json(name = "legs")
    val legs: List<Leg>?
) {

    fun getDuration(): Duration {
        var total = 0.seconds
        legs?.forEach { leg ->
            total += leg.getDuration()
        }
        return total
    }

}