package io.padam_exercise.padamdaily.screens

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.padam_exercise.padamdaily.data.GoogleApiClient
import io.padam_exercise.padamdaily.feature.MapViewModel
import io.padam_exercise.padamdaily.models.MarkerType
import io.padam_exercise.padamdaily.models.Suggestion
import io.padam_exercise.padamdaily.models.mocking.MockSuggestion
import io.padam_exercise.padamdaily.utils.Toolbox
import kotlinx.android.synthetic.main.activity_search_itinerary.*
import kotlinx.android.synthetic.main.fragment_main.*
import padam_exercise.padamdaily.R
import padam_exercise.padamdaily.databinding.FragmentMainBinding

/*
* Create this Fragment for best pratice single activity
* */
class MainFragment : Fragment() {

    private var mMapDelegate: MapActionsDelegate? = null
    lateinit var binding: FragmentMainBinding
    private var apiClient: GoogleApiClient = GoogleApiClient()
    private  lateinit var viewModelRoutesFragment : MapViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelRoutesFragment = ViewModelProvider(requireActivity()).get(MapViewModel::class.java)
        initMapFragment()
        initView()
    }

    private fun initMapFragment() {
        val mapFragment: MapFragment = MapFragment.newInstance()

        childFragmentManager.beginTransaction()
            .replace(R.id.cl_map, mapFragment)
            .commitAllowingStateLoss()

        mMapDelegate = mapFragment
    }

    private fun initView() {
        viewModelRoutesFragment.initListSpinner()
        initSpinners()
        manageOnClickSearchItinerary()
    }

    private fun initSpinners() {

        viewModelRoutesFragment.apply {
            departuresListLive.observe(viewLifecycleOwner){ departuresList ->
                val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, departuresList)
                spinner_departure.adapter = adapter
            }

            arrivalsListLive.observe(viewLifecycleOwner) {arrivalList ->
                val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, arrivalList)
                spinner_arrival.adapter = adapter
            }
        }
    }

    private fun manageOnClickSearchItinerary() {
        btn_search_itinerary.setOnClickListener {
            mMapDelegate?.clearMap()

            val selectedDeparture: String = spinner_departure.selectedItem.toString()
            val departureSuggestion = viewModelRoutesFragment.getSuggestionFromSelection(selectedDeparture, MockSuggestion.departures())

            val selectedArrival: String = spinner_arrival.selectedItem.toString()
            val arrivalSuggestion = viewModelRoutesFragment.getSuggestionFromSelection(selectedArrival, MockSuggestion.arrivals())

            /*
            Place departure and arrival point on the map and send data for update map
             */
            mMapDelegate?.apply {
                updateMarker(MarkerType.DEPARTURE, departureSuggestion)
                updateMarker(MarkerType.ARRIVAL, arrivalSuggestion)
                updateMap(departureSuggestion.latLng, arrivalSuggestion.latLng)
            }
            //Show popup
            showPopUp(arrivalSuggestion.latLng.latitude.toString(),arrivalSuggestion.latLng.longitude.toString())

            /* Api call doesn't work : SSL HANDSHAKE ERROR

       val apiClient = GoogleApiClient().apiClient

        GlobalScope.launch{
            val response = apiClient.getRoadDirections(StartLocation(48.856614,2.3522219), EndLocation(40.4167754,-3.7037902))
            if(response.isSuccessful){
                    Log.d("Google Response", response.toString())
            }*/
        }
    }

    private fun showPopUp(paramLocationLat: String, paramLocationLng: String) {
        AlertDialog.Builder(context).apply {
            setTitle("Travel info")
            setMessage("Time travel = 32") // insert variable time travel fetch with api call
            setCancelable(true)
            setPositiveButton("Open maps"){ _, _ -> openMaps(paramLocationLat,paramLocationLng)}
        }.show()
    }

    private fun openMaps(paramLocationLat: String, paramLocationLng: String) {
        val intent = Uri.parse("geo:$paramLocationLat,$paramLocationLng")
        val mapIntent = Intent(Intent.ACTION_VIEW, intent)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }
}

