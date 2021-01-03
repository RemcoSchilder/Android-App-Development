package com.example.movieappcapstone

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    // Popular movies get request
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "49bacf40c3664a18dbdb08ce0505c6d6",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    // Top rated movies get request
    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("api_key") apiKey: String = "49bacf40c3664a18dbdb08ce0505c6d6",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

    // Upcoming movies get request
    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("api_key") apiKey: String = "49bacf40c3664a18dbdb08ce0505c6d6",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>
}