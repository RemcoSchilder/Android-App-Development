package com.example.rockpaperscissors.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rockpaperscissors.model.Play

@Database(entities = [Play::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class PlayRoomDatabase : RoomDatabase() {

    abstract fun playDao(): PlayDao

    companion object {
        private const val DATABASE_NAME = "RPS_DATABASE"

        @Volatile
        private var playRoomDatabaseInstance: PlayRoomDatabase? = null

        fun getDatabase(context: Context): PlayRoomDatabase? {
            if (playRoomDatabaseInstance == null) {
                synchronized(PlayRoomDatabase::class.java) {
                    if (playRoomDatabaseInstance == null) {
                        playRoomDatabaseInstance = Room.databaseBuilder(
                            context.applicationContext,
                            PlayRoomDatabase::class.java,
                            DATABASE_NAME
                        )
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return playRoomDatabaseInstance
        }
    }

}