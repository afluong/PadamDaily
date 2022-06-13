package io.padam_exercise.padamdaily.data.models

data class StartLocation(
    val lat: Double,
    val lng: Double
){
    override fun toString(): String ="$lat,$lng"
}