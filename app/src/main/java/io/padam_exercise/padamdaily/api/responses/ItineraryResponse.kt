package io.padam_exercise.padamdaily.api.responses

data class ItineraryResponse(
    val status: String,
    val routes: Array<ItineraryDetails>,
)

data class ItineraryDetails(
    val legs: Array<Measure>
)

data class Measure(
    val distance: GeoPoint,
    val duration: GeoPoint
)

data class GeoPoint(
    val text: String,
    val value: Int
)
