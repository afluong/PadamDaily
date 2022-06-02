package io.padam_exercise.padamdaily.api.responses

import io.padam_exercise.padamdaily.api.requests.Point

data class ItineraryResponse(
    val status: String,
    val routes: Array<ItineraryDetails>,
)

data class ItineraryDetails(
    val legs: Array<Measure>
)

data class Measure(
    val distance: GeoPoint,
    val duration: GeoPoint,
    val steps: Array<Step>
)

data class Step(
    val distance: GeoPoint,
    val duration: GeoPoint,
    val end_location: Point
)

data class GeoPoint(
    val text: String,
    val value: Int
)
