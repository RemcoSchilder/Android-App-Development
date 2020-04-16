package com.example.rockpaperscissors.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "play_table")
data class Play(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null,

    @ColumnInfo(name = "date")
    var date: Date,

    @ColumnInfo(name = "player_choice")
    var playerChoice: Int,

    @ColumnInfo(name = "computer_choice")
    var computerChoice: Int,

    @ColumnInfo(name = "result")
    var result: Int

) : Parcelable
