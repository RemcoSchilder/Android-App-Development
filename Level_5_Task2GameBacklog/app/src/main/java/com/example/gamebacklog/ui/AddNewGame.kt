package com.example.gamebacklog.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gamebacklog.R
import com.example.gamebacklog.database.GameRepository
import com.example.gamebacklog.model.Game
import kotlinx.android.synthetic.main.add_game.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.ParseException
import java.text.SimpleDateFormat

const val EXTRA_GAME = "EXTRA_GAME"

class AddNewGame : AppCompatActivity() {

    private lateinit var gameRepository: GameRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_game)
        gameRepository = GameRepository(this)

        initViews()
    }

    private fun initViews() {
        // Setup action bar with title and back button
        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.title_add_activity)
        actionbar.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)

        btnAddPortal.setOnClickListener() {
            val title = etPortalTitle.text.toString()
            val platform = etPortalPlatform.text.toString()
            val releaseDate = etDateDay.text.toString().trim() + "-" + etDateMonth.text.toString().trim() + "-" + etDateYear.text.toString().trim()

            addPlay(title, platform, releaseDate)
        }
    }

    private fun addPlay(title : String, platform : String, releaseDate : String) {
        when {
            title.isBlank() -> {
                Toast.makeText(this,"Please enter title of the game", Toast.LENGTH_SHORT).show()
            }
            platform.isBlank() -> {
                Toast.makeText(this,"Please enter the platform of the game", Toast.LENGTH_SHORT).show()
            }
            !isValidDate(releaseDate) -> {
                Toast.makeText(this,"The date you've entered is not valid, it should be dd-mm-yyyy", Toast.LENGTH_SHORT).show()
            }
            else -> {
                // Create new Game object with the title, platform and release date
                val game = Game(
                    backlog_title = title,
                    platform = platform,
                    release_date = SimpleDateFormat("dd-MM-yyyy").parse(releaseDate)
                )

                val resultIntent = Intent()
                resultIntent.putExtra(EXTRA_GAME, game)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun isValidDate(inputDate: String): Boolean {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")

        try {
            dateFormat.parse(inputDate)
        } catch (pe: ParseException) {
            return false
        }

        return true
    }
}
