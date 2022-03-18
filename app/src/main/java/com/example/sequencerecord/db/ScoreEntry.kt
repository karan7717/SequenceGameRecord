package com.example.sequencerecord.db

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate


@Entity(tableName = "scoreInfo")
data class ScoreEntry @RequiresApi(Build.VERSION_CODES.O) constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val green: Int,
    val blue: Int,
    val type: String,
    var dateGame: String
) {
    init {
        dateGame = LocalDate.now().toString()
    }
}
