package io.padam_exercise.padamdaily.api.requests

data class Point(
    val lat: Double,
    val lng: Double
) {
    override fun toString(): String = "$lat,$lng"
}