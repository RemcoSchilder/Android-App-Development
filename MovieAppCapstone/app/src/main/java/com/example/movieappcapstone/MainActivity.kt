package com.example.movieappcapstone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

// set the movies fragment variable
private const val MOVIES_FRAGMENT = "movies_fragment"
private const val WATCH_LIST_FRAGMENT = "watch_list_fragment"

class MainActivity : AppCompatActivity() {

    // Initiate bottom navigation view
    private lateinit var bottomNavView: BottomNavigationView

    // Main function to set everything up
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Fix bottom navigation buttons
        bottomNavView = findViewById(R.id.bottom_navigation_view)
        bottomNavView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.movies -> showMoviesFragment()
                R.id.watchlist -> showWatchListFragment()
            }

            return@setOnNavigationItemSelectedListener true
        }

        // Standard show movies fragment.
        showMoviesFragment()
    }

    // Function to bind the movies fragment to the main activity.
    private fun showMoviesFragment() {
        val transaction = supportFragmentManager.beginTransaction()

        // Initiate fragments variables for later use.
        val moviesFragment = supportFragmentManager.findFragmentByTag(MOVIES_FRAGMENT)
        val watchListFragment = supportFragmentManager.findFragmentByTag(WATCH_LIST_FRAGMENT)

        // Hide the watchlist fragment when on the movies page.
        watchListFragment?.let { transaction.hide(it) }

        // Show movies screen
        if (moviesFragment == null) {
            transaction.add(R.id.fragment_container, MoviesFragment(), MOVIES_FRAGMENT)
        } else {
            transaction.show(moviesFragment)
        }

        transaction.commit()
    }

    // Function to bind the watchlist fragment to the main activity.
    private fun showWatchListFragment() {
        val transaction = supportFragmentManager.beginTransaction()

        // Initiate fragments variables for later use.
        val moviesFragment = supportFragmentManager.findFragmentByTag(MOVIES_FRAGMENT)
        val watchListFragment = supportFragmentManager.findFragmentByTag(WATCH_LIST_FRAGMENT)

        // Hide the movies fragment when on the movies page.
        moviesFragment?.let { transaction.hide(it) }

        // Show watchlist
        if (watchListFragment == null) {
            transaction.add(R.id.fragment_container, WatchListFragment(), WATCH_LIST_FRAGMENT)
        } else {
            transaction.show(watchListFragment)
        }
        transaction.commit()
    }

}