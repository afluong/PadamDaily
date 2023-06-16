package io.padam_exercise.padamdaily.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.DirectionsApi
import com.google.maps.DirectionsApiRequest
import com.google.maps.GeoApiContext
import com.google.maps.model.DirectionsResult
import com.google.maps.model.LatLng
import io.padam_exercise.padamdaily.R
import io.padam_exercise.padamdaily.models.MarkerType
import io.padam_exercise.padamdaily.models.Suggestion


/**
 * Map Fragment
 * Responsible of displaying map and interactions with it
 */
class MapFragment : Fragment(), OnMapReadyCallback, MapActionsDelegate {

    private var mMap: GoogleMap? = null

    companion object {
        fun newInstance(): MapFragment = MapFragment()
    }

    override fun updateMap(vararg latLngArgs: com.google.android.gms.maps.model.LatLng?) {
        mMap?.let {
            val builder = LatLngBounds.Builder()
            for (latLngArg in latLngArgs) {
                builder.include(latLngArg!!)
            }
            val bounds = builder.build()
            animateMapCamera(bounds)
        }
    }


    override fun updateMarker(markerTypes: List<MarkerType>, suggestions: List<Suggestion>) {

        mMap?.let {

            // for-loop to get Objects! Suggestion and MarkerType
            for (i in suggestions.indices){

                val markerType = markerTypes[i]
                val suggestion = suggestions[i]
                val marker = MarkerOptions()
                    .position(suggestion.latLng)
                    .title(suggestion.name)
                //new value to custom markers color by they type
                val addedMarker = it.addMarker(marker)
                //change made with color: Float parameter at MarkerTypeEnum class
                addedMarker?.setIcon(BitmapDescriptorFactory.defaultMarker(markerType.color))


                //create geoapi object to make an api request
                val context = GeoApiContext.Builder()
                    .apiKey("AIzaSyApBIEIjluNua6Rq-RWIHW-1hSels5nSUs")
                    .build()

                // request to Directions Api about the itinerary. We can specify the daprture city and arrival by origin and destination parameters
                val request: DirectionsApiRequest = DirectionsApi.newRequest(context).origin(LatLng(suggestions[0].latLng.latitude, suggestions[0].latLng.longitude))
                    .destination(LatLng(suggestions[1].latLng.latitude, suggestions[1].latLng.longitude))

                // result of the request with DirectionResult clas, data returned by the Direction Api.
                 val result: DirectionsResult = request.await()


                //flatMap used to transform Polilyne points into LatLng to draw the line
                val polyline = result.routes.flatMap {
                    it.overviewPolyline.decodePath()
                }.map { com.google.android.gms.maps.model.LatLng(it.lat, it.lng) }


                mMap?.addPolyline(PolylineOptions().addAll(polyline))
                }
        }
    }

    override fun clearMap() {
        mMap?.clear()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment: SupportMapFragment? =
            childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {


        mMap = googleMap
        mMap?.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                com.google.android.gms.maps.model.LatLng(47.902964, 1.9092510000000402),
                16f
            )
        )
    }

    private fun animateMapCamera(bounds: LatLngBounds) {
        mMap?.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))
    }
}