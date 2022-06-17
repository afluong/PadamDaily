package io.padam_exercise.padamdaily.screens

import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import io.padam_exercise.padamdaily.models.MarkerType
import io.padam_exercise.padamdaily.models.Suggestion
import padam_exercise.padamdaily.R
import com.google.maps.android.PolyUtil
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

/**
 * Map Fragment
 * Responsible of displaying map and interactions with it
 */
class MapFragment : Fragment(), OnMapReadyCallback, MapActionsDelegate {

    private var mMap: GoogleMap? = null

    companion object {
        fun newInstance(): MapFragment = MapFragment()
    }

    override fun updateMap(vararg latLngArgs: LatLng?) {
        mMap?.let {
            val builder = LatLngBounds.Builder()
            for (latLngArg in latLngArgs) {
                builder.include(latLngArg!!)
            }
            val bounds = builder.build()
            animateMapCamera(bounds)
        }
    }

    override fun drawItinerary(latLngDeparture: LatLng, latLngArrival: LatLng) {
        val path: MutableList<List<LatLng>> = ArrayList()
        val myAPIKEY = requireContext().getPackageManager()
            .getApplicationInfo(requireContext().getPackageName(), PackageManager.GET_META_DATA)
            .metaData.getString("com.google.android.geo.API_KEY")

        val depCoordinate = "" + latLngDeparture.latitude + ","+latLngDeparture.longitude
        val arCoordinate = "" + latLngArrival.latitude + ","+latLngArrival.longitude
        val urlDirections =
            "https://maps.googleapis.com/maps/api/directions/json?origin=$depCoordinate&destination=$arCoordinate&key=$myAPIKEY";
        Log.d("MapFragment", "google api request : $urlDirections");

        val directionsRequest = object : StringRequest(Request.Method.GET, urlDirections, Response.Listener<String> {
                response ->
            val jsonResponse = JSONObject(response)
            Log.d("MapFragment", "google api response : $jsonResponse")

            val routes = jsonResponse.getJSONArray("routes")
            val legs = routes.getJSONObject(0).getJSONArray("legs")
            val steps = legs.getJSONObject(0).getJSONArray("steps")
            for (i in 0 until steps.length()) {
                val points = steps.getJSONObject(i).getJSONObject("polyline").getString("points")
                path.add(PolyUtil.decode(points))
            }

            for (i in 0 until path.size) {
                mMap!!.addPolyline(PolylineOptions().addAll(path[i]).color(Color.RED))
            }

        }, Response.ErrorListener { error ->
            Log.i("MapFragment", error.toString())

        }){}
        val requestQueue = Volley.newRequestQueue(this.context)
        requestQueue.add(directionsRequest)
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