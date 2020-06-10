package com.example.studentportal.database

import androidx.room.*
import com.example.studentportal.model.Backlog

@Dao
interface BacklogDao {
    @Query("SELECT * FROM backlog_table")
    fun getAllGames(): List<Backlog>

    @Insert
    fun insertGame(backlog: Backlog)

    @Delete
    fun deleteSingleGame(backlog: Backlog)

    @Query("DELETE FROM backlog_table")
    fun deleteAllGames()
}