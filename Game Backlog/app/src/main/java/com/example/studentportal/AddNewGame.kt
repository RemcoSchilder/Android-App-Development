package com.example.studentportal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.studentportal.database.BacklogRepository
import com.example.studentportal.model.Backlog
import kotlinx.android.synthetic.main.add_game.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddNewGame : AppCompatActivity() {

    private lateinit var backlogRepository: BacklogRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_game)
        backlogRepository = BacklogRepository(this)

        initViews()
    }

    private fun initViews() {
        val actionbar = supportActionBar
        actionbar!!.title = "AddActivity"
        actionbar.setDisplayHomeAsUpEnabled(true)

        btnAddPortal.setOnClickListener() {
            var title = etPortalTitle.text.toString()
            var platform = etPortalPlatform.text.toString()
            var release_date = etDateDay.text.toString() + " " + etDateMonth.text.toString() + " " + etDateYear.text.toString()

            addPlay(title, platform, release_date)
            this.finish()
        }
    }

    private fun addPlay(title : String, platform : String, releaseDate : String) {
        mainScope.launch {
            // Create new Game object with the title, platform and release date
            val game = Backlog(
                backlogTitle = title,
                platform = platform,
                releaseDate = releaseDate
            )

            withContext(Dispatchers.IO) {
                backlogRepository.insertGame(game)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
