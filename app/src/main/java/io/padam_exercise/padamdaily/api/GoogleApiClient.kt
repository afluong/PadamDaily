package io.padam_exercise.padamdaily.api

import padam_exercise.padamdaily.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GoogleApiClient {
    private const val BASE_URL = BuildConfig.API_URL

    private val gson: Gson by lazy {
        GsonBuilder().setLenient().create()
    }

    private val httpClient: OkHttpClient by lazy {
        val logging = HttpLoggingInterceptor()
        OkHttpClient
            .Builder()
            .addInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val API_SERVICE: GoogleApiService by lazy {
        retrofit.create(GoogleApiService::class.java)
    }
}