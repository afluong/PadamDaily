package io.padam_exercise.padamdaily.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL = "https://maps.googleapis.com/maps/api/"

class GoogleApiClient {

    val apiClient = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GoogleApiServices::class.java)
}