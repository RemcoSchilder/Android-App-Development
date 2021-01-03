package com.example.movieappcapstone

import android.os.Bundle
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop

const val MOVIE_BACKDROP = R.string.MOVIE_BACKDROP
const val MOVIE_POSTER = R.string.MOVIE_POSTER
const val MOVIE_TITLE = R.string.MOVIE_TITLE
const val MOVIE_RATING = R.string.MOVIE_RATING
const val MOVIE_RELEASE_DATE = R.string.MOVIE_RELEASE_DATE
const val MOVIE_OVERVIEW = R.string.MOVIE_OVERVIEW


class MovieDetailsActivity3 : AppCompatActivity() {
    private lateinit var backdrop: ImageView
    private lateinit var poster: ImageView
    private lateinit var title: TextView

    private lateinit var rating: RatingBar
    private lateinit var releaseDate: TextView
    private lateinit var overview: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details3)
        backdrop = findViewById(R.id.movie_backdrop)
        poster = findViewById(R.id.movie_poster)
        title = findViewById(R.id.movie_title)
        rating = findViewById(R.id.movie_rating)
        releaseDate = findViewById(R.id.movie_release_date)
        overview = findViewById(R.id.movie_overview)

        val extras = intent.extras
        if (extras != null) {
            populateDetails(extras)
        } else {
            finish()
        }
    }

    // Function to set de movie details
    private fun populateDetails(extras: Bundle) {
        // Set backdrop image
        extras.getString(MOVIE_BACKDROP.toString())?.let { backdropPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w1280$backdropPath")
                .transform(CenterCrop())
                .into(backdrop)
        }
        //Set movie poster
        extras.getString(MOVIE_POSTER.toString())?.let { posterPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w342$posterPath")
                .transform(CenterCrop())
                .into(poster)
        }

        // Set the remaining details
        title.text = extras.getString(MOVIE_TITLE.toString(), "")
        rating.rating = extras.getFloat(MOVIE_RATING.toString(), 0f) / 2
        releaseDate.text = extras.getString(MOVIE_RELEASE_DATE.toString(), "")
        overview.text = extras.getString(MOVIE_OVERVIEW.toString(), "")
    }
}