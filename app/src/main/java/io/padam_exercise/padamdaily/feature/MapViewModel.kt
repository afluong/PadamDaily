package io.padam_exercise.padamdaily.feature

import android.R
import android.widget.ArrayAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.padam_exercise.padamdaily.models.MarkerType
import io.padam_exercise.padamdaily.models.Suggestion
import io.padam_exercise.padamdaily.models.mocking.MockSuggestion
import io.padam_exercise.padamdaily.screens.MapActionsDelegate
import io.padam_exercise.padamdaily.screens.MapFragment
import io.padam_exercise.padamdaily.utils.Toolbox


/*Using viewModel to avoid business logic in Fragment*/
class MapViewModel : ViewModel(){

    private var mMapDelegate: MapActionsDelegate? = MapFragment.newInstance()


    val departuresListLive : LiveData<ArrayList<String>>
    get()= _departuresListLive
    private val _departuresListLive by lazy {MutableLiveData<ArrayList<String>>()}

    val arrivalsListLive : LiveData<ArrayList<String>>
        get()= _arrivalsListLive
    private val _arrivalsListLive by lazy {MutableLiveData<ArrayList<String>>()}

    val departureSuggestion : MutableLiveData<String>
    get() = _departureSuggestion
    private val _departureSuggestion by lazy { MutableLiveData<String>() }

    val arrivalSuggestion : MutableLiveData<String>
        get() = _arrivalSuggestion
    private val _arrivalSuggestion by lazy { MutableLiveData<String>() }

     fun initListSpinner() {
        _departuresListLive.value = Toolbox.getStringListFromSuggestion(MockSuggestion.departures())
         _arrivalsListLive.value = Toolbox.getStringListFromSuggestion(MockSuggestion.arrivals())
    }
    fun getSuggestion(){
        mMapDelegate?.clearMap()

        val departure = getSuggestionFromSelection(departureSuggestion.value?:"" , MockSuggestion.departures())
        val arrival = getSuggestionFromSelection(arrivalSuggestion.value?:"" , MockSuggestion.arrivals())
        mMapDelegate?.apply {
            updateMarker(MarkerType.DEPARTURE, departure)
            updateMarker(MarkerType.ARRIVAL, arrival)
            updateMap(departure.latLng, arrival.latLng)
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