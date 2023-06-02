package io.padam_exercise.padamdaily.network

import com.squareup.moshi.Json

data class DirectionResponse(
    @Json(name = "routes")
    val routes: List<Route>?
)
