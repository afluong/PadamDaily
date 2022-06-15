package io.padam_exercise.padamdaily.feature


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.padam_exercise.padamdaily.models.Suggestion
import io.padam_exercise.padamdaily.models.mocking.MockSuggestion
import io.padam_exercise.padamdaily.utils.Toolbox


/*Using viewModel to avoid business logic in Fragment*/
class MapViewModel : ViewModel(){

    val departuresListLive : LiveData<ArrayList<String>>
    get()= _departuresListLive
    private val _departuresListLive by lazy {MutableLiveData<ArrayList<String>>()}

    val arrivalsListLive : LiveData<ArrayList<String>>
        get()= _arrivalsListLive
    private val _arrivalsListLive by lazy {MutableLiveData<ArrayList<String>>()}

     fun initListSpinner() {
        _departuresListLive.value = Toolbox.getStringListFromSuggestion(MockSuggestion.departures())
         _arrivalsListLive.value = Toolbox.getStringListFromSuggestion(MockSuggestion.arrivals())
    }

    fun getSuggestionFromSelection(selection: String, suggestionList: ArrayList<Suggestion>): Suggestion {
        for (suggestion in suggestionList) {
            if (suggestion.name == selection) {
                return suggestion
            }
        }
        return suggestionList.first()
    }
}