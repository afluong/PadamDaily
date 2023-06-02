package io.padam_exercise.padamdaily.screens.search_itinerary

import com.google.android.gms.maps.model.LatLng
import io.padam_exercise.padamdaily.models.MarkerType
import io.padam_exercise.padamdaily.models.Suggestion
import io.padam_exercise.padamdaily.network.DirectionResponse

/**
 * Map interface
 * Implement this in your page where there is a map to use map methods
 */
interface MapActionsDelegate {

    fun updateMap(vararg latLngArgs: LatLng?)

    fun updateMarker(markerType: MarkerType, suggestion: Suggestion)

    fun clearMap()

    fun drawItinerary(directionResponse: DirectionResponse)

}
