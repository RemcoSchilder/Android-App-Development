package com.example.movieappcapstone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

// set the movies fragment variable
private const val MOVIES_FRAGMENT = "movies_fragment"

class MainActivity : AppCompatActivity() {

    // Main function to set everything up
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showMoviesFragment()
    }

    // Function to set bind the movies fragment to the main activity.
    private fun showMoviesFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = supportFragmentManager.findFragmentByTag(MOVIES_FRAGMENT)
        if (fragment == null) {
            transaction.add(R.id.fragment_container, MoviesFragment(), MOVIES_FRAGMENT)
        } else {
            transaction.show(fragment)
        }
        transaction.commit()
    }
}