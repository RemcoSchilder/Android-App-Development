package com.example.gamebacklog.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gamebacklog.model.Game

@Dao
interface GameDao {
    @Query("SELECT * FROM game_backlog_table ORDER BY release_date DESC")
    fun getAllGames(): LiveData<List<Game>>

    @Insert
    fun insertGame(game: Game)

    @Delete
    fun deleteGame(game: Game)

    @Query("DELETE FROM game_backlog_table")
    fun deleteAllGames()
}