package com.example.studentportal.database

import android.content.Context
import com.example.studentportal.model.Backlog

class BacklogRepository(context: Context) {

    private val backlogDao: BacklogDao

    init {
        val database =
            BacklogRoomDatabase.getDatabase(context)
        backlogDao = database!!.backlogDao()
    }

    suspend fun getAllGames(): List<Backlog> = backlogDao.getAllGames()

    suspend fun insertGame(backlog: Backlog) = backlogDao.insertGame(backlog)

    suspend fun deleteSingleGame(backlog: Backlog) = backlogDao.deleteSingleGame(backlog)

    suspend fun deleteAllGames() = backlogDao.deleteAllGames()

}