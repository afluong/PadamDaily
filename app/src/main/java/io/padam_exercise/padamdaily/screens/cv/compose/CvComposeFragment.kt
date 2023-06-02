package io.padam_exercise.padamdaily.screens.cv.compose

import android.os.Bundle
import android.view.View
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import padam_exercise.padamdaily.R
import padam_exercise.padamdaily.databinding.FragmentCvComposeBinding

class CvComposeFragment : Fragment(R.layout.fragment_cv_compose) {

    private lateinit var binding: FragmentCvComposeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCvComposeBinding.bind(view)

        binding.composable.setContent {
            CvScreen()
        }
    }
}

@Composable
fun CvScreen() {
    Text(text = "CV screen test")
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CvScreenPreview() {
    CvScreen()
}