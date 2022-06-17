package io.padam_exercise.padamdaily.screens

import android.R.color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FabPosition.Companion.Center
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import padam_exercise.padamdaily.R


class ResumeComposeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                Column(Modifier.padding(20.dp)
                    .verticalScroll(rememberScrollState())
                ) {
                    Presentation(
                        getString(R.string.resume_name),
                        getString(R.string.resume_role),
                        getString(R.string.resume_phone),
                        getString(R.string.resume_mail),
                        getString(R.string.resume_city)
                    )
                    TitleResume(getString(R.string.resume_experience));
                    ShowExperience(getString(R.string.resume_exp1_title),
                        getString(R.string.resume_exp1_society),
                        getString(R.string.resume_exp1_year),
                        getString(R.string.resume_exp1_body))
                    ShowExperience(getString(R.string.resume_exp2_title),
                        getString(R.string.resume_exp2_society),
                        getString(R.string.resume_exp2_year),
                        getString(R.string.resume_exp2_body))
                    ShowExperience(getString(R.string.resume_exp3_title),
                        getString(R.string.resume_exp3_society),
                        getString(R.string.resume_exp3_year),
                        getString(R.string.resume_exp3_body))

                    TitleResume(getString(R.string.resume_education));
                    ShowSchool(getString(R.string.resume_school1_title),
                        getString(R.string.resume_school1_year),
                        getString(R.string.resume_school1_body))
                    ShowSchool(getString(R.string.resume_school2_title),
                        getString(R.string.resume_school2_year),
                        getString(R.string.resume_school2_body))
                }
            }
        }
    }

    @Composable
    fun ShowExperience(title : String, society : String, year : String, body : String) {
        Spacer(modifier = Modifier.height(15.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = title,
                color = colorResource(R.color.colorPrimary),
                style = MaterialTheme.typography.h6,
            )
            Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween){
                Text(
                    text = society,
                    color = colorResource(R.color.colorPrimaryDark),
                    style = MaterialTheme.typography.body1,
                )
                Text(
                    text = year,
                    color = colorResource(R.color.colorPrimaryDark),
                    style = MaterialTheme.typography.body1,
                )
            }
            Text(
                text = body
            )
        }
    }

    @Composable
    fun ShowSchool (title: String, year : String, body : String) {
        Spacer(modifier = Modifier.height(15.dp))
        Column(modifier = Modifier.fillMaxWidth()) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    color = colorResource(R.color.colorPrimary),
                    style = MaterialTheme.typography.h6,
                )
                Text(
                    text = year,
                    color = colorResource(R.color.colorPrimaryDark),
                    style = MaterialTheme.typography.body1,
                )
            }
            Text(
                text = body)
        }
    }
    @Composable
    fun TitleResume(title : String) {
        Spacer(modifier = Modifier.height(25.dp))
        Text(
            text = title,
            color = colorResource(R.color.colorPrimary),
            style = MaterialTheme.typography.h5,
        )
    }
    @Composable
    fun Presentation(name: String, job: String, phone: String, mail: String, city:String) {
        Column (modifier = Modifier.fillMaxWidth()) {
            Text(
                text = name,
                color = colorResource(R.color.colorPrimary),
                style = MaterialTheme.typography.h3,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center

            )
            Text(
                text = job,
                color = colorResource(R.color.colorAccent),
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Italic,
                modifier = Modifier
                    .fillMaxWidth()

            )
            Spacer(modifier = Modifier.height(15.dp))
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround ){
                Text(text = phone)
                Text(text = mail)
                Text(text = city)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}