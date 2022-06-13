package padam_exercise.padamdaily

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.model.LatLng
import io.padam_exercise.padamdaily.data.GoogleApiApiClient
import io.padam_exercise.padamdaily.data.models.EndLocation
import io.padam_exercise.padamdaily.data.models.StartLocation
import io.padam_exercise.padamdaily.models.MarkerType
import io.padam_exercise.padamdaily.models.Suggestion
import io.padam_exercise.padamdaily.models.mocking.MockSuggestion
import io.padam_exercise.padamdaily.screens.MapActionsDelegate
import io.padam_exercise.padamdaily.screens.MapFragment
import io.padam_exercise.padamdaily.screens.SearchItineraryActivity.Companion.TAG
import io.padam_exercise.padamdaily.utils.Toolbox
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import padam_exercise.padamdaily.databinding.FragmentMainBinding
import retrofit2.Retrofit
import java.util.stream.DoubleStream.builder


/**
 *
 */
class MainFragment : Fragment() {

    private var mMapDelegate: MapActionsDelegate? = null
    lateinit var binding: FragmentMainBinding
    private var apiClient: GoogleApiApiClient = GoogleApiApiClient()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        initSpinners()
        manageOnClickSearchItinerary()
    }

    private fun initSpinners() {
        initDepartureSpinner()
        initArrivalSpinner()
    }

    private fun initDepartureSpinner() {
        val departuresList = Toolbox.getStringListFromSuggestion(MockSuggestion.departures())
        val adapter = ArrayAdapter(requireActivity().applicationContext, android.R.layout.simple_list_item_1, departuresList)
        spinner_departure.adapter = adapter
    }

    private fun initArrivalSpinner() {
        val arrivalsList = Toolbox.getStringListFromSuggestion(MockSuggestion.arrivals())
        val adapter =
            this.context?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, arrivalsList) }
        spinner_arrival.adapter = adapter
    }

    private fun manageOnClickSearchItinerary() {
        btn_search_itinerary.setOnClickListener {
            mMapDelegate?.clearMap()

            val selectedDeparture: String = spinner_departure.selectedItem.toString()
            val departureSuggestion =
                getSuggestionFromSelection(selectedDeparture, MockSuggestion.departures())

            val selectedArrival: String = spinner_arrival.selectedItem.toString()
            val arrivalSuggestion =
                getSuggestionFromSelection(selectedArrival, MockSuggestion.arrivals())

            /*
            Place departure and arrival point on the map and send data for update map
             */
            mMapDelegate?.apply {
                updateMarker(MarkerType.DEPARTURE, departureSuggestion)
                updateMarker(MarkerType.ARRIVAL, arrivalSuggestion)
                updateMap(departureSuggestion.latLng, arrivalSuggestion.latLng)
            }

           getRoadItinary(StartLocation(46.00,12.000),EndLocation(48.000,2.05))

            showPopUp(arrivalSuggestion.latLng.latitude.toString(),arrivalSuggestion.latLng.longitude.toString())
        }
    }

    private fun getRoadItinary(origin : StartLocation , end : EndLocation){
        runBlocking {
            val response = apiClient.api.getRoadDirections(origin,end)

            if(response.isSuccessful){
                Log.d("TAG", "getRoadItinary: $response")
            }
        }
       /* GlobalScope.launch(Dispatchers.IO) {

            val response = apiClient.api.getRoadDirections(origin,end)

            if(response.isSuccessful){
                Log.d("TAG", "getRoadItinary: $response")
            }
        }*/

    }

    private fun getURL(from : LatLng, to : LatLng) : String {
        val origin = "origin=" + from.latitude + "," + from.longitude
        val dest = "destination=" + to.latitude + "," + to.longitude
        val sensor = "sensor=false"
        val mode ="mode=driving"
        val params = "$origin&$dest&$sensor&$mode"
        return "https://maps.googleapis.com/maps/api/directions/json?$params"
    }

    private fun showPopUp(paramLocationLat: String, paramLocationLng: String) {
        AlertDialog.Builder(context).apply {
            setTitle("Travel info")
            setMessage("Time travel = 32")
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

    private fun getSuggestionFromSelection(
        selection: String,
        suggestionList: ArrayList<Suggestion>
    ): Suggestion {
        for (suggestion in suggestionList) {
            if (suggestion.name == selection) {
                return suggestion
            }
        }
        return suggestionList.first()
    }

}