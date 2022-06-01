package io.padam_exercise.padamdaily.api

import io.padam_exercise.padamdaily.api.requests.Point
import io.padam_exercise.padamdaily.api.responses.ItineraryResponse
import padam_exercise.padamdaily.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = BuildConfig.API_KEY

interface GoogleApiService {
    @GET("directions/json?key=$API_KEY")
    suspend fun getItinerary(
        @Query("origin") origin: Point,
        @Query("destination") destination: Point
    ): Response<ItineraryResponse>
}