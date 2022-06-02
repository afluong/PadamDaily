package io.padam_exercise.padamdaily.api.requests

import com.google.android.gms.maps.model.LatLng

data class Point(
    val lat: Double,
    val lng: Double
) {
    override fun toString(): String = "$lat,$lng"
}

fun Point.toLatLng() = LatLng(lat, lng)