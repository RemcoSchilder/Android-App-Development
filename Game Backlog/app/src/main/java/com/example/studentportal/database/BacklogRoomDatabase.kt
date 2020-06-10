package com.example.studentportal.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.studentportal.model.Backlog

@Database(entities = [Backlog::class], version = 1, exportSchema = false)
abstract class BacklogRoomDatabase : RoomDatabase() {

    abstract fun backlogDao(): BacklogDao

    companion object {
        private const val DATABASE_NAME = "BACKLOG_DATABASE"

        @Volatile
        private var backlogRoomDatabaseInstance: BacklogRoomDatabase? = null

        fun getDatabase(context: Context): BacklogRoomDatabase? {
            if (backlogRoomDatabaseInstance == null) {
                synchronized(BacklogRoomDatabase::class.java) {
                    if (backlogRoomDatabaseInstance == null) {
                        backlogRoomDatabaseInstance = Room.databaseBuilder(
                            context.applicationContext,
                            BacklogRoomDatabase::class.java,
                            DATABASE_NAME
                        )
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return backlogRoomDatabaseInstance
        }
    }

}