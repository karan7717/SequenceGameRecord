package com.example.sequencerecord.score

import android.app.Application
import androidx.lifecycle.*
import com.example.sequencerecord.Repository.ScoreRepository

import com.example.sequencerecord.db.ScoreEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ScoreFragmentViewModel( app : Application , private val scoreRepository: ScoreRepository) : ViewModel() {

    val valueScoreLiveData = MutableLiveData<ScoreValues>()
    val scoreEntryLiveData = MutableLiveData<ScoreEntry?>()
    val allScoreLiveData = MutableLiveData<List<ScoreEntry?>>()

    init {

        getAllScores()
    }

    fun getAllScores(){
        viewModelScope.launch(Dispatchers.IO){
            allScoreLiveData.postValue(scoreRepository.getAllScoreInfo())
        }
       }
    fun getScoreViaId(Id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            scoreEntryLiveData.postValue(scoreRepository.getScoreViaId(Id))
        }
    }
    fun insertScoreInfo(entry: ScoreEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            scoreRepository.insertScore(entry)
            getAllScores()
        }
    }

    fun updateScoreInfo(entry: ScoreEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            scoreRepository.updateScore(entry)
            getAllScores()
        }

    }

    fun deleteScoreInfo(entry: ScoreEntry) {
        deleteScore(entry.green,entry.blue,entry.type)
        viewModelScope.launch(Dispatchers.IO) {
            scoreRepository.deleteScore(entry)
            getAllScores()
        }

    }


    fun getScoreAndResult() {
        valueScoreLiveData.postValue(scoreRepository.getScoreAndResult())
    }



    fun addScore(green : Int,blue: Int, type: String){
        scoreRepository.addScore(green,blue,type)

    }


    fun deleteScore(green : Int ,  blue : Int ,  type: String){
        scoreRepository.deleteScore(green,blue,type)

    }

}


