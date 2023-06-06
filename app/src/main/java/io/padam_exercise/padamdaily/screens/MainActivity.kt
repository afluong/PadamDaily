package io.padam_exercise.padamdaily.screens

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import padam_exercise.padamdaily.R
import padam_exercise.padamdaily.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController : NavController = navHostFragment.navController

        // Handle item selection
        return when (item.itemId) {
            R.id.action_navigation -> {
                navController.navigate(R.id.SearchItineraryFragment)
                true
            }
            R.id.action_resume -> {
                navController.navigate(R.id.ResumeFragment)
                true
            }
            R.id.action_compose_resume -> {
                navController.navigate(R.id.composeFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}