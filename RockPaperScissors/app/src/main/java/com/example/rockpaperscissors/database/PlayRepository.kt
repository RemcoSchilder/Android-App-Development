package com.example.rockpaperscissors.database

import android.content.Context
import com.example.rockpaperscissors.model.Play
import com.example.rockpaperscissors.model.TotalStatistic

class PlayRepository(context: Context) {

    private val playDao: PlayDao

    init {
        val database =
            PlayRoomDatabase.getDatabase(context)
        playDao = database!!.playDao()
    }

    suspend fun getAllPlays(): List<Play> = playDao.getAllPlays()

    suspend fun insertPlay(play: Play) = playDao.insertPlay(play)

    suspend fun deleteAllPlays() = playDao.deleteAllPlays()

    fun getStatistics(): List<TotalStatistic> = playDao.getStatistics()

}