package io.padam_exercise.padamdaily.screens

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import io.padam_exercise.padamdaily.models.MarkerType
import io.padam_exercise.padamdaily.models.mocking.MockSuggestion
import io.padam_exercise.padamdaily.models.Suggestion
import io.padam_exercise.padamdaily.utils.Toolbox
import io.padam_exercise.padamdaily.viewmodel.SearchItineraryViewModel
import kotlinx.coroutines.launch
import padam_exercise.padamdaily.R
import padam_exercise.padamdaily.databinding.ActivitySearchItineraryBinding
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class SearchItineraryActivity : AppCompatActivity() {

    private val binding by lazy { ActivitySearchItineraryBinding.bind(findViewById(R.id.root_layout)) }

    private var mMapDelegate: MapActionsDelegate? = null

    private val viewModel by lazy { SearchItineraryViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_itinerary)

        initMapFragment()
        initView()

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.itineraryFlow.collect { response ->
                    response?.let { response ->
                        val directionResponse = response.first
                        val origin = response.second
                        val destination = response.third
                        if (directionResponse.routes.isNullOrEmpty()) {
                            Toast.makeText(this@SearchItineraryActivity, R.string.no_itinerary, Toast.LENGTH_LONG).show()
                            return@collect
                        }
                        directionResponse.routes.firstOrNull()?.let { firstRoute ->
                            val duration = firstRoute.getDuration()
                            if (duration > 0L.seconds) {
                                displayDialog(duration, origin, destination)
                                mMapDelegate?.drawItinerary(directionResponse)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun displayDialog(duration: Duration, origin: Suggestion, destination: Suggestion) {
        var fragment = supportFragmentManager.findFragmentByTag(ItineraryBottomSheetDialogFragment.TAG) as DialogFragment?
        if (fragment == null) {
            fragment = ItineraryBottomSheetDialogFragment.newInstance(duration, origin.latLng, destination.latLng)
        }
        if (!fragment.isAdded) {
            fragment.show(supportFragmentManager, ItineraryBottomSheetDialogFragment.TAG)
        }
    }

    private fun initMapFragment() {
        val mapFragment: MapFragment = MapFragment.newInstance()

        supportFragmentManager.beginTransaction()
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
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, departuresList)
        binding.spinnerDeparture.adapter = adapter
    }

    private fun initArrivalSpinner() {
        val arrivalsList = Toolbox.getStringListFromSuggestion(MockSuggestion.arrivals())
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrivalsList)
        binding.spinnerArrival.adapter = adapter
    }

    private fun manageOnClickSearchItinerary() {
        binding.btnSearchItinerary.setOnClickListener {
            mMapDelegate?.clearMap()

            val selectedDeparture: String = binding.spinnerDeparture.selectedItem.toString()
            val suggestionDeparture = getSuggestionFromSelection(selectedDeparture, MockSuggestion.departures())
            mMapDelegate?.updateMarker(MarkerType.DEPARTURE, suggestionDeparture)

            val selectedArrival: String = binding.spinnerArrival.selectedItem.toString()
            val suggestionArrival = getSuggestionFromSelection(selectedArrival, MockSuggestion.arrivals())
            mMapDelegate?.updateMarker(MarkerType.ARRIVAL, suggestionArrival)

            mMapDelegate?.updateMap(suggestionDeparture.latLng, suggestionArrival.latLng)

            viewModel.getItinerary(getString(R.string.google_maps_key), suggestionDeparture, suggestionArrival)
        }
    }

    private fun getSuggestionFromSelection(selection: String, suggestionList: ArrayList<Suggestion>): Suggestion {
        for (suggestion in suggestionList) {
            if (suggestion.name == selection) {
                return suggestion
            }
        }
        return suggestionList.first()
    }
}