package io.padam_exercise.padamdaily.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.padam_exercise.padamdaily.models.Suggestion
import io.padam_exercise.padamdaily.network.DirectionResponse
import io.padam_exercise.padamdaily.network.GoogleMapApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import padam_exercise.padamdaily.BuildConfig
import padam_exercise.padamdaily.R
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class SearchItineraryViewModel: ViewModel() {

    private val _itineraryFlow = MutableStateFlow(null as DirectionResponse?)
    internal val itineraryFlow: StateFlow<DirectionResponse?>
        get() = _itineraryFlow


    fun getItinerary(apiKey: String, suggestionDeparture: Suggestion, suggestionArrival: Suggestion) =
        viewModelScope.launch {
            itineraryFlow(apiKey, suggestionDeparture, suggestionArrival)
                .collect {
                    _itineraryFlow.emit(it)
           }
        }

    private suspend fun itineraryFlow(apiKey: String, suggestionDeparture: Suggestion, suggestionArrival: Suggestion) = flow {
        val googleMapsApi = createRetrofit().create(GoogleMapApi::class.java)
        val origin = "${suggestionDeparture.latLng.latitude},${suggestionDeparture.latLng.longitude}"
        val destination = "${suggestionArrival.latLng.latitude},${suggestionArrival.latLng.longitude}"
        val direction = googleMapsApi.getDirection(apiKey, origin, destination)
        emit(direction)
    }
    .flowOn(Dispatchers.IO)

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(createMoshi()))
            .baseUrl(BuildConfig.GOOGLE_MAPS_BASE_URL)
            .client(createOkHttpClient())
            .build()
    }

    private fun createMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            })
            .build()
    }
}