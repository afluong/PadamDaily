package io.padam_exercise.padamdaily

import com.google.android.gms.maps.model.LatLng
import io.padam_exercise.padamdaily.models.Suggestion
import io.padam_exercise.padamdaily.utils.Toolbox
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class UtilsUnitTest {


    val ss : ArrayList<Suggestion> = ArrayList()
    @Before
    fun setUp(){
        val paris = Suggestion("Paris", LatLng(48.8588897, 2.320041))
        val orleans = Suggestion("Orléans", LatLng(47.9027336, 1.9086066))
        val lille = Suggestion("Lille", LatLng(50.6365654, 3.0635282))
        val strasbourg = Suggestion("Strasbourg", LatLng(48.584614, 7.7507127))
        val suggestions = listOf(paris, orleans, lille, strasbourg)
        ss.addAll(suggestions)

    }

    @Test
    fun getString(){


        val stringList = Toolbox.getStringListFromSuggestion(suggestionList = ss)
        val expectedList = arrayListOf("Paris", "Orléans", "Lille", "Strasbourg")
        assertEquals(stringList, expectedList)
    }



}