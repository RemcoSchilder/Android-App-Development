package com.example.movieappcapstone

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop

const val MOVIE_BACKDROP = R.string.MOVIE_BACKDROP
const val MOVIE_POSTER = R.string.MOVIE_POSTER
const val MOVIE_TITLE = R.string.MOVIE_TITLE
const val MOVIE_RATING = R.string.MOVIE_RATING
const val MOVIE_RELEASE_DATE = R.string.MOVIE_RELEASE_DATE
const val MOVIE_OVERVIEW = R.string.MOVIE_OVERVIEW
const val MOVIE_ID = R.string.MOVIE_ID


class MovieDetailsActivity3 : AppCompatActivity() {
    private lateinit var backdrop: ImageView
    private lateinit var poster: ImageView
    private lateinit var title: TextView
    private lateinit var rating: RatingBar
    private lateinit var releaseDate: TextView
    private lateinit var overview: TextView
    private lateinit var addToWatchList: Button

    // Movie detail database variables
    private var movieId = 0L
    private var movieBackdrop = ""
    private var moviePoster = ""
    private var movieTitle = ""
    private var movieRating = 0f
    private var movieReleaseDate = ""
    private var movieOverview = ""

    // Set the database variable
    // Use lazy so that the database only gets initialized when this variable is accessed.
    private val db: AppDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "movies.db"
        ).allowMainThreadQueries().build()
    }

    // Get movie from database
    // Returns a nullable MovieEntity since a movie might not exist in the database
    private fun getMovie(id: Long): MovieEntity? {
        return db.movieDao().findById(id)
    }

    // Initialize all the variables on create
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_movie_details)
        backdrop = findViewById(R.id.movie_backdrop)
        poster = findViewById(R.id.movie_poster)
        title = findViewById(R.id.movie_title)
        rating = findViewById(R.id.movie_rating)
        releaseDate = findViewById(R.id.movie_release_date)
        overview = findViewById(R.id.movie_overview)
        addToWatchList = findViewById(R.id.add_to_watch_list)

        // set the details
        val extras = intent.extras
        if (extras != null) {
            populateDetails(extras)
        } else {
            finish()
        }
    }

    // Function to set de movie details
    private fun populateDetails(extras: Bundle) {
        // Set de database variables.
        movieId = extras.getLong(MOVIE_ID.toString())
        movieBackdrop = extras.getString(MOVIE_BACKDROP.toString(), "")
        moviePoster = extras.getString(MOVIE_POSTER.toString(), "")
        movieTitle = extras.getString(MOVIE_TITLE.toString(), "")
        movieRating = extras.getFloat(MOVIE_RATING.toString(), 0f)
        movieReleaseDate = extras.getString(MOVIE_RELEASE_DATE.toString(), "")
        movieOverview = extras.getString(MOVIE_OVERVIEW.toString(), "")


        // Set backdrop image
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w1280$movieBackdrop")
            .transform(CenterCrop())
            .into(backdrop)

        //Set movie poster
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w342$moviePoster")
            .transform(CenterCrop())
            .into(poster)

        // Set the remaining details
        title.text = movieTitle
        rating.rating = movieRating
        releaseDate.text = movieReleaseDate
        overview.text = movieOverview

        // If movie is not in your watchlist, then the text will say "add to watchlist". If it is in your watchlist it will say "remove from watchlist".
        val movie = getMovie(movieId)
        if (movie == null) {
            addToWatchList.text = getString(R.string.add_to_watch_list)
        } else {
            addToWatchList.text = getString(R.string.remove_from_watch_list)
        }
    }

    override fun onStart() {
        super.onStart()

        // Set on click for the add to watchlist button.
        addToWatchList.setOnClickListener {
            // If movie doesn't exist in your database it will insert it. If it does exist it will be removed from the watchlist.
            if (getMovie(movieId) == null) {
                val entity = MovieEntity(
                    movieId,
                    movieTitle,
                    movieOverview,
                    moviePoster,
                    movieBackdrop,
                    movieRating,
                    movieReleaseDate
                )
                db.movieDao().insert(entity)
                addToWatchList.text = getString(R.string.remove_from_watch_list)
            } else {
                db.movieDao().delete(movieId)
                addToWatchList.text = getString(R.string.add_to_watch_list)
            }
        }
    }
}