package com.example.studentportal.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "backlog_table")
data class Backlog(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null,

    @ColumnInfo(name = "backlog_title")
    var backlogTitle: String,

    @ColumnInfo(name = "platform")
    var platform: String,

    @ColumnInfo(name = "release_date")
    var releaseDate: String

) : Parcelable
