package com.example.studentportal

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.studentportal.database.BacklogRepository
import com.example.studentportal.model.Backlog
import kotlinx.android.synthetic.main.game_activity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val ADD_PORTAL_REQUEST_CODE = 15

class OverviewPortalActivity : AppCompatActivity() {

    private val gameAdapter = GameAdapter()
    private lateinit var backlogRepository: BacklogRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_activity)
        //setSupportActionBar(toolbar)
        backlogRepository = BacklogRepository(this)

        initViews()
    }

    private fun initViews() {
        // Open add portal activity
        add_portal.setOnClickListener {
            val intent = Intent(this, AddNewGame::class.java)
            startActivityForResult(intent, ADD_PORTAL_REQUEST_CODE)
        }

        // Create list
        rvPortals.apply {
            layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
            adapter = gameAdapter
        }

        // Retrieve the game backlog from the database
        getGameBacklog()
    }

    override fun onResume() {
        super.onResume()

        // Refresh portal overview
        getGameBacklog()
        gameAdapter.notifyDataSetChanged()
    }

    private fun getGameBacklog() {
        // Retrieve all history
        mainScope.launch {
            val gameBacklog = withContext(Dispatchers.IO) {
                backlogRepository.getAllGames()
            }

            this@OverviewPortalActivity.gameAdapter.setGames(gameBacklog)
            this@OverviewPortalActivity.gameAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_backlog, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menuDeleteGamesBtn -> {
            // Delete all games
            mainScope.launch {
                withContext(Dispatchers.IO) {
                    backlogRepository.deleteAllGames()
                }
            }
            getGameBacklog()
            true
        }
        else ->
            super.onOptionsItemSelected(item)
    }
}
