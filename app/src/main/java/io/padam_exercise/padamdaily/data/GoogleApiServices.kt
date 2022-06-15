package io.padam_exercise.padamdaily.data


import io.padam_exercise.padamdaily.data.models.EndLocation
import io.padam_exercise.padamdaily.data.models.MyItinaryResponse
import io.padam_exercise.padamdaily.data.models.StartLocation
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "AIzaSyBlTAwfQA5D-U8S8PSS8bl9dlf56vR-lKc"

interface GoogleApiServices {

    @GET("/directions/json?key=$API_KEY")
    suspend fun getRoadDirections(
        @Query("origin")  origin : StartLocation,
       @Query("destination") destination: EndLocation
    ) : Response<MyItinaryResponse>


}