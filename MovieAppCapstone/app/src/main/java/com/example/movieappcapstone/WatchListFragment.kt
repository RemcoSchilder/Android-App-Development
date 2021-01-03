package com.example.movieappcapstone

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room

class WatchListFragment : Fragment() {
    // Initiate the recycler view and watchlist adapter
    private lateinit var watchList: RecyclerView
    private lateinit var watchListAdapter: WatchListAdapter

    // Set the database variable
    // Use lazy so that the database only gets initialized when this variable is accessed.
    private val db: AppDatabase by lazy {
        Room.databaseBuilder(
            activity!!.applicationContext,
            AppDatabase::class.java,
            "movies.db"
        ).allowMainThreadQueries().build()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_watch_list, container, false)

        // Bind the data to the grid
        watchList = view.findViewById(R.id.watchlist)
        watchList.layoutManager = GridLayoutManager(context, 3)
        watchListAdapter = WatchListAdapter(listOf()) {movie -> showMovieDetails(movie)}
        watchList.adapter = watchListAdapter

        // Call the function to get de data from the database.
        getWatchList()

        return view
    }

    // get the watchlist from the database
    private fun getWatchList() {
        val movies = db.movieDao().getAll()
        val watchList = mutableListOf<WatchList>()
        watchList.addAll(
            movies.map { movie ->
                WatchList(
                    movie.id,
                    movie.title,
                    movie.overview,
                    movie.posterPath,
                    movie.backdropPath,
                    movie.rating,
                    movie.releaseDate
                )
            }
        )
        // Update the watchlist
        watchListAdapter.updateItems(watchList)
    }

    // Call these two functions when the fragment is shown to refresh the list
    override fun onHiddenChanged(hidden: Boolean) {
        if (!hidden) {
            getWatchList()
        }
    }

    override fun onResume() {
        super.onResume()
        getWatchList()
    }

    // Function to show the details of a movie.
    private fun showMovieDetails(item: WatchList) {
        // I used intent to "glue" the details from the request to the movie details activity.
        val intent = Intent(activity, MovieDetailsActivity::class.java)
        intent.putExtra(MOVIE_ID.toString(), item.id)
        intent.putExtra(MOVIE_BACKDROP.toString(), item.backdropPath)
        intent.putExtra(MOVIE_POSTER.toString(), item.posterPath)
        intent.putExtra(MOVIE_TITLE.toString(), item.title)
        intent.putExtra(MOVIE_RATING.toString(), item.rating)
        intent.putExtra(MOVIE_RELEASE_DATE.toString(), item.releaseDate)
        intent.putExtra(MOVIE_OVERVIEW.toString(), item.overview)

        startActivity(intent)
    }
}