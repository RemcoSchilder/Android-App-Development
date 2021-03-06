package com.example.movieappcapstone

import com.google.gson.annotations.SerializedName

// Response format
data class GetMoviesResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: List<Movie>,
    @SerializedName("total_pages") val pages: Int
)