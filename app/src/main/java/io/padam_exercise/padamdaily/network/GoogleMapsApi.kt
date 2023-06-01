package io.padam_exercise.padamdaily.network;

import padam_exercise.padamdaily.BuildConfig
import retrofit2.http.GET;
import retrofit2.http.Query

interface GoogleMapApi {

    /**
     * https://developers.google.com/maps/documentation/directions/get-directions
     * Get a direction between an origin and a destination
     */
    @GET("directions/json?sensor=false&mode=driving")
    suspend fun getDirection(
        @Query("key") apiKey: String,
        @Query("origin") origin: String,
        @Query("destination") destination: String
    ): Any

}