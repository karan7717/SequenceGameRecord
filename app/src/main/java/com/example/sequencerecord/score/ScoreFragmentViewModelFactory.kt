package com.example.sequencerecord.score

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sequencerecord.Repository.ScoreRepository

class ScoreFragmentViewModelFactory(private val app : Application, private val scoreRepository: ScoreRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ScoreFragmentViewModel(app,scoreRepository) as T
    }
}