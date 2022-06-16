package io.padam_exercise.padamdaily.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import padam_exercise.padamdaily.R
import padam_exercise.padamdaily.databinding.FragmentComposeBinding


class ComposeFragment : Fragment() {

    lateinit var binding: FragmentComposeBinding
    private var experienceCard = listOf(
        listOf(
            "Android Developper",
            "Lyon",
            "1 years"
        ), listOf(
            "Android Developper",
            "Lyon",
            "2 years"
        ), listOf(
            "Android Developper",
            "Lille",
            "1 years"
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentComposeBinding.inflate(inflater, container, false)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                Column {
                    Row(modifier = Modifier.padding(all = 8.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_launcher_background),
                            contentDescription = "Profile picture"
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Column() {
                            Text(text = "Android Developper")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "RICHARD Alex")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "26 years old")
                        }

                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Conversation(messages = experienceCard)
                }
            }
        }
        return binding.root
    }

    @Composable
    fun Conversation(messages: List<List<String>>) {
        LazyColumn {
            items(messages.size) { message ->
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "Profile picture of entreprise"
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(text = messages[message][0])
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = messages[message][1])
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = messages[message][2])
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
