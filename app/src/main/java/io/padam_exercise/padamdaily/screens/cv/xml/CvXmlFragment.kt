package io.padam_exercise.padamdaily.screens.search_itinerary.xml

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import padam_exercise.padamdaily.R
import padam_exercise.padamdaily.databinding.FragmentCvXmlBinding

class CvXmlFragment : Fragment(R.layout.fragment_cv_xml) {

    private lateinit var binding: FragmentCvXmlBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCvXmlBinding.bind(view)
    }

}