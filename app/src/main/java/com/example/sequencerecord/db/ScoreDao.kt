package com.example.sequencerecord.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface ScoreDao {

    @Query("Select * From scoreInfo Order By id Desc")
    suspend fun getAllScoreInfo(): List<ScoreEntry?>

    @Query("Select * From scoreInfo where id = :Id")
    suspend fun getScoreViaId(Id: Int): ScoreEntry?

    @Insert
    suspend fun insertScore(score: ScoreEntry)

    @Delete
    suspend fun deleteScore(score: ScoreEntry)

    @Update
    suspend fun updateScore(score: ScoreEntry)

}