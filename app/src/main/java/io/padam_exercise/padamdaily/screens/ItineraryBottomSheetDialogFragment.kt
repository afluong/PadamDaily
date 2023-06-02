package io.padam_exercise.padamdaily.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.padam_exercise.padamdaily.TimeUtils.displayHoursAndMinutes
import padam_exercise.padamdaily.R
import padam_exercise.padamdaily.databinding.ItineraryDialogBinding
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

class ItineraryBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: ItineraryDialogBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.itinerary_dialog, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = ItineraryDialogBinding.bind(view)

        val duration = requireArguments().getLong(DURATION).seconds
        binding.itineraryDialogTextView.text = duration.displayHoursAndMinutes()

        binding.itineraryDialogTextViewCancel.setOnClickListener {
            dismiss()
        }
    }

    companion object {
        val TAG: String = ItineraryBottomSheetDialogFragment::class.java.simpleName
        const val DURATION = "DURATION"

        fun newInstance(duration: Duration): ItineraryBottomSheetDialogFragment {
            val fragment = ItineraryBottomSheetDialogFragment()
            val bundle = Bundle().apply {
                putLong(DURATION, duration.toLong(DurationUnit.SECONDS))
            }
            fragment.arguments = bundle
            return fragment
        }
    }
}
