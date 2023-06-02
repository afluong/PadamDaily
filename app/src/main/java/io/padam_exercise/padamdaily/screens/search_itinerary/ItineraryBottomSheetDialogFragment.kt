package io.padam_exercise.padamdaily.screens.search_itinerary

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.padam_exercise.padamdaily.Utils
import io.padam_exercise.padamdaily.Utils.displayHoursAndMinutes
import io.padam_exercise.padamdaily.Utils.parcelable
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

        val origin: LatLng? = requireArguments().parcelable(ORIGIN)
        val destination: LatLng? = requireArguments().parcelable(DESTINATION)
        binding.itineraryDialogTextViewOpen.setOnClickListener {
            if (origin != null && destination != null) {
                val uri = Utils.getGoogleMapsUrl(origin, destination)
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri)))
            }
        }
    }

    companion object {
        val TAG: String = ItineraryBottomSheetDialogFragment::class.java.simpleName
        const val DURATION = "DURATION"
        const val ORIGIN = "ORIGIN"
        const val DESTINATION = "DESTINATION"

        fun newInstance(duration: Duration, origin: LatLng, destination: LatLng): ItineraryBottomSheetDialogFragment {
            val fragment = ItineraryBottomSheetDialogFragment()
            val bundle = Bundle().apply {
                putLong(DURATION, duration.toLong(DurationUnit.SECONDS))
                putParcelable(ORIGIN, origin)
                putParcelable(DESTINATION, destination)
            }
            fragment.arguments = bundle
            return fragment
        }
    }
}
