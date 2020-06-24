package com.example.gamebacklog.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.gamebacklog.R
import com.example.gamebacklog.database.GameRepository
import com.example.gamebacklog.model.Game
import kotlinx.android.synthetic.main.game_activity.*

const val ADD_GAME_REQUEST_CODE = 15
const val TAG = "GameOverview"

class GameOverview : AppCompatActivity() {

    private val games = arrayListOf<Game>()
    private val gameAdapter = GameAdapter(games)
    private lateinit var gameRepository: GameRepository
    private val viewModel: GameOverviewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_activity)
        gameRepository = GameRepository(this)

        initViews()
        observeViewModel()
    }

    private fun initViews() {
        // Open add portal activity
        add_portal.setOnClickListener {
            val intent = Intent(this, AddNewGame::class.java)
            startActivityForResult(intent,
                ADD_GAME_REQUEST_CODE
            )
        }

        // Create list
        rvPortals.apply {
            layoutManager = StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL)
            adapter = gameAdapter
        }
        createItemTouchHelper().attachToRecyclerView(rvPortals)
    }

    private fun observeViewModel() {
        viewModel.games.observe(this, Observer { games ->
            this@GameOverview.games.clear()
            this@GameOverview.games.addAll(games)
            gameAdapter.notifyDataSetChanged()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_game, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menuDeleteGamesBtn -> {
            // Delete all games
            viewModel.deleteAllGames()
            true
        }
        else ->
            super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ADD_GAME_REQUEST_CODE -> {
                    data?.let { safeData ->
                        val game = safeData.getParcelableExtra<Game>(EXTRA_GAME)
                        game?.let { safeGame ->
                            viewModel.insertGame(safeGame)
                        } ?: run {
                            Log.e(TAG, "game is null")
                        }
                    } ?: run {
                        Log.e(TAG, "empty intent data received")
                    }
                }
            }
        }
    }

    private fun createItemTouchHelper(): ItemTouchHelper {
        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val gameToDelete = games[position]

                viewModel.deleteGame(gameToDelete)
            }
        }
        return ItemTouchHelper(callback)
    }
}
