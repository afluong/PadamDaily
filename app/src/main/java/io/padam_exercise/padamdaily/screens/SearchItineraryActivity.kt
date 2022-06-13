package io.padam_exercise.padamdaily.screens

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import padam_exercise.padamdaily.R
import padam_exercise.padamdaily.databinding.ActivitySearchItineraryBinding


class SearchItineraryActivity : AppCompatActivity() {

    private lateinit var navController : NavController
    private var mMapDelegate: MapActionsDelegate? = null
    lateinit var binding: ActivitySearchItineraryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchItineraryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.my_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.navmenu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.resumePage-> Navigation.findNavController(this,R.id.my_fragment).navigate(R.id.resumeFragment)
            R.id.searchPage-> Navigation.findNavController(this,R.id.my_fragment).navigate(R.id.mainFragment2)
        }
        return super.onOptionsItemSelected(item)
    }



    /*
    ** here for debug
    */
    companion object {
        val TAG: String = "SearchItineraryActivity"
    }
}