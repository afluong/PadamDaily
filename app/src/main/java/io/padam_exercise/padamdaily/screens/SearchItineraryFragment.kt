package io.padam_exercise.padamdaily.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import io.padam_exercise.padamdaily.models.MarkerType
import io.padam_exercise.padamdaily.models.Suggestion
import io.padam_exercise.padamdaily.models.mocking.MockSuggestion
import io.padam_exercise.padamdaily.utils.Toolbox
import padam_exercise.padamdaily.R
import padam_exercise.padamdaily.databinding.FragmentSearchItineraryBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SearchItineraryFragment : Fragment() {

    private var _binding: FragmentSearchItineraryBinding? = null
    private var mMapDelegate: MapActionsDelegate? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchItineraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMapFragment()
        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
        val departureAdapter =
            ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, departuresList)
        binding.spinnerDeparture.adapter = departureAdapter
    }

    private fun initArrivalSpinner() {
        val arrivalsList = Toolbox.getStringListFromSuggestion(MockSuggestion.arrivals())
        val arrivalAdapter =
            ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, arrivalsList)
        binding.spinnerArrival.adapter = arrivalAdapter
    }

    private fun manageOnClickSearchItinerary() {
        binding.btnSearchItinerary.setOnClickListener {

            val selectedDeparture: String = binding.spinnerDeparture.selectedItem.toString()
            val departureSuggestion =
                getSuggestionFromSelection(selectedDeparture, MockSuggestion.departures())

            val selectedArrival: String = binding.spinnerArrival.selectedItem.toString()
            val arrivalSuggestion =
                getSuggestionFromSelection(selectedArrival, MockSuggestion.arrivals())

            mMapDelegate?.run {
                clearMap()
                updateMarker(MarkerType.DEPARTURE, departureSuggestion)
                updateMarker(MarkerType.ARRIVAL, arrivalSuggestion)
                updateMap(departureSuggestion.latLng, arrivalSuggestion.latLng)
            }
        }
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