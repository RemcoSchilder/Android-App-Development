package com.example.rockpaperscissors.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.rockpaperscissors.R
import com.example.rockpaperscissors.database.PlayRepository
import com.example.rockpaperscissors.model.Play
import kotlinx.android.synthetic.main.activity_play.*
import kotlinx.android.synthetic.main.fragment_play_result.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


const val SHOW_HISTORY_REQUEST_CODE = 17

class PlayActivity : AppCompatActivity() {

    private lateinit var playRepository: PlayRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        setSupportActionBar(toolbar)
        playRepository = PlayRepository(this)
        initViews()
    }

    private fun initViews() {
        val clickListener = View.OnClickListener {view ->
            var playerChoice = 0
            val pcChoice = (0..2).random()
            var pcChoiceImage: Int
            var result = 0;

            if(pcChoice == 0){ pcChoiceImage = R.drawable.rock }
            else if (pcChoice == 1) { pcChoiceImage = R.drawable.paper }
            else { pcChoiceImage = R.drawable.scissors }

            ivPC.setImageResource(pcChoiceImage)

            when (view.id) {
                R.id.ivRock -> {
                    ivPlayer.setImageResource(R.drawable.rock)
                }
                R.id.ivPaper -> {
                    playerChoice = 1
                    ivPlayer.setImageResource(R.drawable.paper)
                }
                R.id.ivScissor -> {
                    playerChoice = 2
                    ivPlayer.setImageResource(R.drawable.scissors)
                }
                else -> {}
            }

            if (playerChoice == 0 && pcChoice == 2 || playerChoice == 1 && pcChoice == 0 || playerChoice == 2 && pcChoice == 1) {
                tvWinMessage.text = "You Win!"
            } else if (playerChoice == pcChoice) {
                tvWinMessage.text = "Draw!"
                result = 1
            } else {
                tvWinMessage.text = "You lose!"
                result = 2
            }

            addPlay(playerChoice, pcChoice, result)

            include.visibility = View.VISIBLE
        }

        ivRock.setOnClickListener(clickListener)
        ivPaper.setOnClickListener(clickListener)
        ivScissor.setOnClickListener(clickListener)

        getStatistics()
    }

    private fun addPlay(playerChoice : Int, computerChoice : Int, result : Int) {
        mainScope.launch {
            // Create new Play object with the current date, player and computer choice and the result (0 -> win, 1 -> draw or 2 -> lose)
            val play = Play(
                date = Date(),
                playerChoice = playerChoice,
                computerChoice = computerChoice,
                result = result
            )

            withContext(Dispatchers.IO) {
                playRepository.insertPlay(play)
                getStatistics()
            }
        }
    }

    private fun getStatistics() {
        mainScope.launch {
            val playStatistics = withContext(Dispatchers.IO) {
                playRepository.getStatistics()
            }

            var win = 0
            var draw = 0
            var lose = 0
            for (stat in playStatistics) {
                if (stat.result == 0) { win = stat.total }
                if (stat.result == 1) { draw = stat.total }
                if (stat.result == 2) { lose = stat.total }
            }

            if (win == 0 && draw == 0 && lose == 0) {
                include.visibility = View.INVISIBLE
            }

            tvStatistics.text = "Win: %d Draw: %d Lose: %d"
        }
    }

    override fun onResume() {
        super.onResume()

        // Update statistics on back to this activity
        getStatistics()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_play, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menuShowHistoryBtn -> {
            // Open history activity
            val intent = Intent(this, HistoryActivity::class.java)
            startActivityForResult(intent,
                SHOW_HISTORY_REQUEST_CODE
            )
            true
        }
        else ->
            super.onOptionsItemSelected(item)
    }
}