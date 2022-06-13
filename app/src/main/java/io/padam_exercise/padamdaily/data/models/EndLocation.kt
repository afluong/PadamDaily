package io.padam_exercise.padamdaily.data.models

data class EndLocation(
    val lat: Double,
    val lng: Double
){
    override fun toString(): String ="$lat,$lng"
}