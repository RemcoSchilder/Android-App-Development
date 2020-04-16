package com.example.rockpaperscissors.database

import androidx.room.*
import com.example.rockpaperscissors.model.Play
import com.example.rockpaperscissors.model.TotalStatistic

@Dao
interface PlayDao {
    @Query("SELECT * FROM play_table ORDER BY date DESC")
    fun getAllPlays(): List<Play>

    @Insert
    fun insertPlay(play: Play)

    @Query("DELETE FROM play_table")
    fun deleteAllPlays()

    @Query("SELECT result, COUNT(result) AS total FROM play_table GROUP BY result")
    fun getStatistics(): List<TotalStatistic>
}