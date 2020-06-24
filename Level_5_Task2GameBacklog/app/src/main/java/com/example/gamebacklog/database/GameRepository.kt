package com.example.gamebacklog.database

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.gamebacklog.model.Game

class GameRepository(context: Context) {

    private val gameDao: GameDao

    init {
        val database =
            GameRoomDatabase.getDatabase(context)
        gameDao = database!!.gameDao()
    }

    fun getAllGames(): LiveData<List<Game>> = gameDao.getAllGames()

    fun insertGame(game: Game) = gameDao.insertGame(game)

    fun deleteGame(game: Game) = gameDao.deleteGame(game)

    fun deleteAllGames() = gameDao.deleteAllGames()

}