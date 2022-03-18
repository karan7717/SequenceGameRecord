package com.example.sequencerecord.Repository

import android.app.Application
import android.content.Context
import android.graphics.Color
import com.example.sequencerecord.score.*

import com.example.sequencerecord.db.RoomAppDB

import com.example.sequencerecord.db.ScoreEntry
private const val GREEN_J = "greenJ"
private const val BLUE_J = "blueJ"
private const val GREEN_NONJ = "greenNonJ"
private const val BLUE_NONJ = "blueNonJ"

class ScoreRepository( private val app : Application) {
    val dao by lazy{ RoomAppDB.getAppDatabase(app)?.scoreDao() }
    val scoreSharedPref= app.getSharedPreferences(
        "score_pref", Context.MODE_PRIVATE
    )

    suspend fun getAllScoreInfo(): List<ScoreEntry?> {
        return dao!!.getAllScoreInfo()
    }


     suspend fun getScoreViaId(Id: Int): ScoreEntry? {
        return dao!!.getScoreViaId(Id)
    }


    suspend fun insertScore(score: ScoreEntry){
        return dao!!.insertScore(score)
    }


    suspend fun deleteScore(score: ScoreEntry){
        return dao!!.deleteScore(score)
    }


    suspend fun updateScore(score: ScoreEntry){
        println("green ${score.green} blue ${score.blue}  type ${score.type} id = ${score.id} ")
        return dao!!.updateScore(score)
    }
    fun addJScore(green: Int, blue: Int) {

        var greenResult = scoreSharedPref.getInt(GREEN_J, 0)
        greenResult = greenResult + green
        var blueResult = scoreSharedPref.getInt(BLUE_J, 0)
        blueResult = blueResult + blue
        val editor = scoreSharedPref.edit()
        editor.putInt(GREEN_J, greenResult)
        editor.putInt(BLUE_J, blueResult)
        editor.apply()

    }
    fun addNonJScore(green: Int, blue: Int) {
        var greenResult = scoreSharedPref.getInt(GREEN_NONJ, 0)
        greenResult = greenResult + green
        var blueResult = scoreSharedPref.getInt(BLUE_NONJ, 0)
        blueResult = blueResult + blue
        val editor = scoreSharedPref.edit()
        editor.putInt(GREEN_NONJ, greenResult)
        editor.putInt(BLUE_NONJ, blueResult)
        editor.apply()

    }

    fun getScoreAndResult() : ScoreValues{
        var greenJResult = scoreSharedPref.getInt(GREEN_J, 0)
        var greenNonJResult = scoreSharedPref.getInt(GREEN_NONJ, 0)
        var blueJResult = scoreSharedPref.getInt(BLUE_J, 0)
        var blueNonJResult = scoreSharedPref.getInt(BLUE_NONJ, 0)
        var leadingJScoreResult: Int
        var textJColor = 0
        var leadingNonJScoreResult: Int
        var textNonJColor = 0
        var greenTotalScore: Int
        var blueTotalScore: Int
        var leadingTotalScore: Int
        var leadingScoreColor = 0

        var resultJ = scoreResult(greenJResult, blueJResult)
        leadingJScoreResult = resultJ.first
        textJColor = resultJ.second


        var resultNonJ = scoreResult(greenNonJResult, blueNonJResult)
        leadingNonJScoreResult = resultNonJ.first
        textNonJColor = resultNonJ.second


        greenTotalScore = greenJResult + greenNonJResult
        blueTotalScore = blueJResult + blueNonJResult
        var resultTotal = scoreResult(greenTotalScore, blueTotalScore)
        leadingTotalScore = resultTotal.first
        leadingScoreColor = resultTotal.second


        val scoreValue = ScoreValues(
            greenJResult,
            greenNonJResult,
            blueJResult,
            blueNonJResult,
            leadingJScoreResult,
            leadingNonJScoreResult,
            greenTotalScore,
            blueTotalScore,
            leadingTotalScore,
            textJColor,
            textNonJColor,
            leadingScoreColor
        )

       return scoreValue

    }

    fun scoreResult(green: Int, blue: Int): Pair<Int, Int> {
        var total = 0
        var color = Color.LTGRAY
        if (green > blue) {
            total = green - blue
            color = Color.GREEN
        } else if (blue > green) {
            total = blue - green
            color = Color.BLUE
        } else {
            total = 0

        }
        return Pair<Int, Int>(first = total, second = color)
    }

    fun addScore(green : Int,blue: Int, type: String){
        println("into score add")
        if(type.equals("J")){
            addJScore(green,blue)
        }else{
            addNonJScore(green,blue)
        }
    }


    fun deleteScore(green : Int ,  blue : Int ,  type: String){
        if(type.equals("J")){
            deleteJScore(green,blue)
        }else{
            deleteNonJScore(green,blue)
        }
    }
    fun deleteJScore(green:Int,blue:Int){
        var greenResult = scoreSharedPref.getInt(GREEN_J, 0)
        greenResult = greenResult - green
        var blueResult = scoreSharedPref.getInt(BLUE_J, 0)
        blueResult = blueResult - blue
        val editor = scoreSharedPref.edit()
        editor.putInt(GREEN_J, greenResult)
        editor.putInt(BLUE_J, blueResult)
        editor.apply()

    }
    fun deleteNonJScore(green:Int,blue:Int){
        var greenResult = scoreSharedPref.getInt(GREEN_NONJ, 0)
        greenResult = greenResult - green
        var blueResult = scoreSharedPref.getInt(BLUE_NONJ, 0)
        blueResult = blueResult - blue
        val editor = scoreSharedPref.edit()
        editor.putInt(GREEN_NONJ, greenResult)
        editor.putInt(BLUE_NONJ, blueResult)
        editor.apply()

    }


}