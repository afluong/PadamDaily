package io.padam_exercise.padamdaily.data.models

data class MyItinaryResponse(
    val geocoded_waypoints: List<GeocodedWaypoint>,
    val routes: List<Route>,
    val status: String
)