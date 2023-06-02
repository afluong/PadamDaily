package io.padam_exercise.padamdaily.screens.cv.compose

import android.icu.text.SymbolTable
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val scrollState = rememberScrollState()

    Row {
        Column(
            modifier = Modifier
                .weight(0.4f)
                .fillMaxHeight()
                .background(colorResource(R.color.colorBackground))
        ){
            val image = painterResource(id = R.drawable.image_sophian_laroy)
            Image(
                modifier = Modifier
                    .size(100.dp)
                    .padding(start = 8.dp, top = 24.dp)
                    .align(Alignment.CenterHorizontally),
                painter = image,
                contentDescription = ""
            )
            Contact()
            Expertises()
        }
        Column(
            modifier = Modifier
                .weight(0.6f)
                .fillMaxHeight()
                .background(Color.White)
                .verticalScroll(scrollState)
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 8.dp, top = 24.dp),
                text = "Sophian Laroy",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier
                    .padding(start = 8.dp, top = 4.dp),
                text = "Développeur Android Senior",
                fontSize = 18.sp
            )
            Text(
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp),
                text = stringResource(id = R.string.resume),
                fontSize = 10.sp
            )
            CVHeader1(
                text = "Expériences",
                modifier = Modifier
                    .padding(start = 8.dp, top = 16.dp),
            )
            Line(modifier = Modifier
                .padding(start = 8.dp, top = 4.dp)
            )
            Experience(
                name = "EasyMovie",
                dates = "2019 - 2023",
                experiences = stringResource(id = R.string.experiences_easymovie)
            )
            Experience(
                name = "PayMyTable",
                dates = "2016 - 2019",
                experiences = stringResource(id = R.string.experiences_paymytable)
            )
            Spacer(
                modifier = Modifier.height(24.dp)
            )
        }
    }
}

@Composable
fun Experience(
    name: String,
    dates: String,
    experiences: String
) {
    CVHeader2(
        modifier = Modifier
            .padding(start = 8.dp, top = 8.dp),
        text = name
    )
    Text(
        modifier = Modifier
            .padding(start = 8.dp),
        text = dates,
        fontSize = 12.sp
    )
    Text(
        modifier = Modifier
            .padding(start = 8.dp, top = 4.dp),
        text = experiences,
        fontSize = 12.sp
    )
}

@Composable
fun Contact() {
    CVHeader1(
        modifier = Modifier
            .padding(start = 8.dp, top = 24.dp),
        text = "Contact"
    )
    Line(modifier = Modifier
            .padding(start = 8.dp, top = 4.dp)
    )
    CVHeader2(
        modifier = Modifier
            .padding(start = 8.dp, top = 8.dp),
        text = "Téléphone"
    )
    Text(
        modifier = Modifier
            .padding(start = 8.dp, top = 4.dp),
        fontSize = 12.sp,
        text = "06 47 95 49 89"
    )
    CVHeader2(
        modifier = Modifier
            .padding(start = 8.dp, top = 8.dp),
        text = "Email"
    )
    Text(
        modifier = Modifier
            .padding(start = 8.dp, top = 4.dp),
        fontSize = 12.sp,
        text = "sophian.laroy@gmail.com"
    )
}

@Composable
fun Expertises() {
    CVHeader1(
        modifier = Modifier
            .padding(start = 8.dp, top = 24.dp),
        text = "Expertises"
    )
    Line(modifier = Modifier
        .padding(start = 8.dp, top = 4.dp)
    )
    CVHeader2(
        modifier = Modifier
            .padding(start = 8.dp, top = 8.dp),
        text = "Hard Skills"
    )
    Text(
        modifier = Modifier
            .padding(start = 8.dp, top = 4.dp),
        fontSize = 12.sp,
        text = stringResource(id = R.string.hard_skills)
    )
    CVHeader2(
        modifier = Modifier
            .padding(start = 8.dp, top = 8.dp),
        text = "Soft Skills"
    )
    Text(
        modifier = Modifier
            .padding(start = 8.dp, top = 4.dp),
        fontSize = 12.sp,
        text = stringResource(id = R.string.soft_skills)
    )
}

@Composable
fun CVHeader1(modifier: Modifier = Modifier, text: String) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun CVHeader2(modifier: Modifier = Modifier, text: String) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun Line(modifier: Modifier = Modifier) {
    Divider(
        modifier =  modifier,
        color = colorResource(R.color.colorAccent),
        thickness = 1.dp
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CvScreenPreview() {
    CvScreen()
}