package io.padam_exercise.padamdaily.screens

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
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
import io.padam_exercise.padamdaily.api.requests.toLatLng
import io.padam_exercise.padamdaily.models.MarkerType
import io.padam_exercise.padamdaily.models.Suggestion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import padam_exercise.padamdaily.R

/**
 * Map Fragment
 * Responsible of displaying map and interactions with it
 */
class MapFragment : Fragment(), OnMapReadyCallback, MapActionsDelegate {

    private var mMap: GoogleMap? = null
    private var points = arrayListOf<LatLng>()
    private var distance = 0
    private var time = 0

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
            getItineraryPoints(latLngArgs) { travel ->
                drawItinerary(map, travel)
            }
        }
    }

    private fun getItineraryPoints(
        latLngArgs: Array<out LatLng?>,
        validateTravel: (travel: Array<LatLng>) -> Unit
    ) {
        lifecycleScope.launch(Dispatchers.Default) {
            try {
                val departurePoint = latLngArgs[0]?.let {
                    Point(it.latitude, it.longitude)
                }!!
                val arrivalPoint = latLngArgs[1]?.let {
                    Point(it.latitude, it.longitude)
                }!!
                val response =
                    GoogleApiClient.API_SERVICE.getItinerary(departurePoint, arrivalPoint)

                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d("status API", it.status)
                        points.clear()
                        distance = 0
                        time = 0
                        it.routes[0].legs[0].steps.forEach { step ->
                            points.add(step.end_location.toLatLng())
                            distance += step.distance.value
                            time += step.duration.value
                        }
                    }
                } else {
                    points.run {
                        add(LatLng(departurePoint.lat, departurePoint.lng))
                        add(LatLng(departurePoint.lat, departurePoint.lng))
                    }
                }
                withContext(Dispatchers.Main) { validateTravel(points.toTypedArray()) }
            } catch (error: Exception) {
                Log.e("error API", "Api Call")
            }
        }
    }

    private fun drawItinerary(map: GoogleMap, latLngArgs: Array<LatLng>) {
        val lines = with(PolylineOptions()) {
            latLngArgs.forEach {
                add(it)
            }
            geodesic(true)
            color(Color.RED)
        }
        map.addPolyline(lines)
        popupTravel(latLngArgs[1])
    }

    private fun popupTravel(latLng: LatLng) {
        AlertDialog.Builder(context).apply {
            setTitle("temps: ${time.toDouble() / 60} h")
            setMessage("distance: ${distance.toDouble() / 1000} km")
            setPositiveButton("maps") { _, _ -> openGoogleMaps(latLng) }
        }.show()
    }

    private fun openGoogleMaps(latLng: LatLng) {
        val gmmIntentUri =
            Uri.parse("google.navigation:q=${latLng.latitude},${latLng.longitude}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
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
        points.clear()
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