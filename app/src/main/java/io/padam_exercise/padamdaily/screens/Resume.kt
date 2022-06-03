package io.padam_exercise.padamdaily.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Resume() {
    Text(text = "Resume", color = Color.Red)
}

@Preview
@Composable
fun ResumePreview() {
    Resume()
}