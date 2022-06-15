package io.padam_exercise.padamdaily.screens

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import padam_exercise.padamdaily.R
import padam_exercise.padamdaily.databinding.ActivitySearchItineraryBinding

class SearchItineraryActivity : AppCompatActivity() {

    private lateinit var navController : NavController
    lateinit var binding: ActivitySearchItineraryBinding
    private var mMapDelegate: MapActionsDelegate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchItineraryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.my_fragment) as NavHostFragment
        navController = navHostFragment.navController

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.resumePage-> Navigation.findNavController(this,R.id.my_fragment).navigate(R.id.resumeFragment)
            R.id.searchPage-> Navigation.findNavController(this,R.id.my_fragment).navigate(R.id.mainFragment)
        }
        return super.onOptionsItemSelected(item)
    }
}
