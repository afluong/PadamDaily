package io.padam_exercise.padamdaily.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import io.padam_exercise.padamdaily.models.MarkerType
import io.padam_exercise.padamdaily.models.mocking.MockSuggestion
import io.padam_exercise.padamdaily.models.Suggestion
import io.padam_exercise.padamdaily.utils.Toolbox
import padam_exercise.padamdaily.R
import padam_exercise.padamdaily.databinding.FragmentSearchItineraryBinding

class SearchItineraryFragment : Fragment() {

    private var mMapDelegate: MapActionsDelegate? = null
    private var _binding: FragmentSearchItineraryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchItineraryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initMapFragment()
        initView()
        return root
    }

    private fun initMapFragment() {
        val mapFragment: MapFragment = MapFragment.newInstance()

        requireActivity().supportFragmentManager.beginTransaction()
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
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, departuresList)
        binding.spinnerDeparture.adapter = adapter
    }

    private fun initArrivalSpinner() {
        val arrivalsList = Toolbox.getStringListFromSuggestion(MockSuggestion.arrivals())
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, arrivalsList)
        binding.spinnerArrival.adapter = adapter
    }

    private fun manageOnClickSearchItinerary() {
        binding.btnSearchItinerary.setOnClickListener {
            mMapDelegate?.clearMap()

            val selectedDeparture: String = binding.spinnerDeparture.selectedItem.toString()
            val selectedArrival: String = binding.spinnerArrival.selectedItem.toString()

            val suggestionDeparture = getSuggestionFromSelection(selectedDeparture, MockSuggestion.departures())
            val suggestionArrival = getSuggestionFromSelection(selectedArrival, MockSuggestion.arrivals())

            updateMap(suggestionDeparture, suggestionArrival)

            mMapDelegate?.drawItinerary(suggestionDeparture.latLng, suggestionArrival.latLng)

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


    private fun updateMap(suggestionDeparture: Suggestion, suggestionArrival: Suggestion){
        mMapDelegate?.clearMap()

        mMapDelegate?.updateMarker(MarkerType.DEPARTURE, suggestionDeparture)
        mMapDelegate?.updateMarker(MarkerType.ARRIVAL, suggestionArrival)

        mMapDelegate?.updateMap(suggestionDeparture.latLng, suggestionArrival.latLng)

    }
}