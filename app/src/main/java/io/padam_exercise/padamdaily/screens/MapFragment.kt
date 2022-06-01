package io.padam_exercise.padamdaily.screens

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import io.padam_exercise.padamdaily.api.GoogleApiClient
import io.padam_exercise.padamdaily.api.requests.Point
import io.padam_exercise.padamdaily.models.MarkerType
import io.padam_exercise.padamdaily.models.Suggestion
import kotlinx.coroutines.launch
import padam_exercise.padamdaily.R

/**
 * Map Fragment
 * Responsible of displaying map and interactions with it
 */
class MapFragment : Fragment(), OnMapReadyCallback, MapActionsDelegate {

    private var mMap: GoogleMap? = null

    init {
        apiCall()
    }

    companion object {
        fun newInstance(): MapFragment = MapFragment()
    }

    override fun updateMap(vararg latLngArgs: LatLng?) {
        mMap?.let { map ->
            val builder = LatLngBounds.Builder()
            for (latLngArg in latLngArgs) {
                builder.include(latLngArg!!)
            }
            val bounds = builder.build()
            animateMapCamera(bounds)
            drawItinerary(map, latLngArgs)
        }
    }

    private fun apiCall() {
        lifecycleScope.launch {
            val response = GoogleApiClient.API_SERVICE.getItinerary(
                Point(48.8588897,2.320041),
                Point(47.9027336,1.9086066)
            )
            if (response.isSuccessful) {
                response.body()?.let {
                    Log.d("status API", it.status)
                }
            }
        }
    }

    private fun drawItinerary(map: GoogleMap, latLngArgs: Array<out LatLng?>) {
        val lines = with(PolylineOptions()) {
            latLngArgs.forEach {
                add(it)
            }
            geodesic(true)
            color(Color.RED)
        }
        map.addPolyline(lines)
    }

    override fun updateMarker(markerType: MarkerType, suggestion: Suggestion) {
        mMap?.let {
            val marker = MarkerOptions()
                .position(suggestion.latLng)
                .title(suggestion.name)
            it.addMarker(marker)
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
                LatLng(47.902964, 1.9092510000000402),
                16f
            )
        )
    }

    private fun animateMapCamera(bounds: LatLngBounds) {
        mMap?.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))
    }
}