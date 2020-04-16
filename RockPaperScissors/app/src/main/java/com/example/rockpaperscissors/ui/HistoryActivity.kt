package com.example.rockpaperscissors.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.database.PlayRepository
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryActivity : AppCompatActivity() {

    private val playAdapter = PlayAdapter()
    private lateinit var playRepository: PlayRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setSupportActionBar(toolbar)
        playRepository = PlayRepository(this)
        initViews()
    }

    private fun initViews() {
        // Setup action bar with title and back button
        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.history_title)
        actionbar.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);

        // Initialize history recyclerview
        rvHistory.layoutManager = LinearLayoutManager(applicationContext)
        rvHistory.adapter = playAdapter
        rvHistory.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        // Retrieve the play history
        getPlayHistory()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_history, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        // Delete all history from database
        R.id.menuDeleteHistoryBtn -> {
            mainScope.launch {
                withContext(Dispatchers.IO) {
                    playRepository.deleteAllPlays()
                }
            }
            this.finish() // Back to PlayActivity
            true
        }
        else ->
            super.onOptionsItemSelected(item)
    }

    private fun getPlayHistory() {
        // Retrieve all history
        mainScope.launch {
            val playHistory = withContext(Dispatchers.IO) {
                playRepository.getAllPlays()
            }

            this@HistoryActivity.playAdapter.setHistory(playHistory)
            this@HistoryActivity.playAdapter.notifyDataSetChanged()
        }
    }

}
