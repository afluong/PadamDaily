package io.padam_exercise.padamdaily.data.models

data class GeocodedWaypoint(
    val geocoder_status: String,
    val place_id: String,
    val types: List<String>
)