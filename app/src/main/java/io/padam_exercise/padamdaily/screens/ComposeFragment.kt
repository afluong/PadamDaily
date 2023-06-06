package io.padam_exercise.padamdaily.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.padam_exercise.padamdaily.models.Job
import padam_exercise.padamdaily.R
import padam_exercise.padamdaily.databinding.FragmentComposeBinding

class ComposeFragment : Fragment() {

    private var _binding: FragmentComposeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentComposeBinding.inflate(inflater, container, false)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        NameCard()
                        Resume(
                            jobs = listOf(
                                Job(
                                    stringResource(R.string.job1_title),
                                    stringResource(R.string.job1_desc),
                                    stringResource(R.string.job1_missions),
                                    stringResource(R.string.job1_tech)
                                ),
                                Job(
                                    stringResource(R.string.job2_title),
                                    stringResource(R.string.job2_desc),
                                    stringResource(R.string.job2_missions),
                                    stringResource(R.string.job2_tech)
                                ),
                                Job(
                                    stringResource(R.string.job3_title),
                                    stringResource(R.string.job3_desc),
                                    stringResource(R.string.job3_missions),
                                    stringResource(R.string.job3_tech)
                                ),
                                Job(
                                    stringResource(R.string.job4_title),
                                    stringResource(R.string.job4_desc),
                                    stringResource(R.string.job4_missions),
                                    stringResource(R.string.job4_tech)
                                )
                            )
                        )
                    }
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @Composable
    fun NameCard() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.default_padding)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.android),
                contentDescription = "Android logo",
                modifier = Modifier
                    .size(60.dp)
            )
            Text(
                text = stringResource(R.string.resume_name),
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = dimensionResource(R.dimen.default_header).value.sp
                )
            )
        }
    }

    @Composable
    fun JobCard(job: Job) {
        Box(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.default_padding))
                .clip(RoundedCornerShape(dimensionResource(R.dimen.corner_radius)))
                .background(colorResource(R.color.colorPrimary))
                .padding(dimensionResource(R.dimen.default_padding))
        ) {
            Column {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colorResource(R.color.colorPrimaryDark))
                        .padding(dimensionResource(R.dimen.default_padding))
                ) {
                    Text(
                        text = job.getJobTitle(),
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.background,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    )
                }

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.default_margin)))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.background)
                        .padding(dimensionResource(R.dimen.default_padding))
                ) {
                    Text(text = job.getJobDesc())
                }

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.default_margin)))

                Text(
                    text = stringResource(R.string.label_job_missions),
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.background)
                        .padding(dimensionResource(R.dimen.default_padding))
                ) {
                    Text(text = job.getJobMissions())
                }

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.default_margin)))

                Text(
                    text = stringResource(R.string.label_job_tech),
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.background)
                        .padding(dimensionResource(R.dimen.default_padding))
                ) {
                    Text(text = job.getJobTech())
                }
            }
        }
    }

    @Composable
    fun Resume(jobs: List<Job>) {
        LazyColumn {
            items(jobs) { job ->
                JobCard(job)
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun ComposeResumePreview() {
        Column {
            NameCard()
            Resume(
                jobs = listOf(
                    Job(
                        stringResource(R.string.job1_title),
                        stringResource(R.string.job1_desc),
                        stringResource(R.string.job1_missions),
                        stringResource(R.string.job1_tech)
                    ),
                    Job(
                        stringResource(R.string.job2_title),
                        stringResource(R.string.job2_desc),
                        stringResource(R.string.job2_missions),
                        stringResource(R.string.job2_tech)
                    ),
                    Job(
                        stringResource(R.string.job3_title),
                        stringResource(R.string.job3_desc),
                        stringResource(R.string.job3_missions),
                        stringResource(R.string.job3_tech)
                    ),
                    Job(
                        stringResource(R.string.job4_title),
                        stringResource(R.string.job4_desc),
                        stringResource(R.string.job4_missions),
                        stringResource(R.string.job4_tech)
                    )
                )
            )
        }
    }
}