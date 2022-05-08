package com.example.sequencerecord

import android.app.Application
import com.example.sequencerecord.Repository.ScoreRepository
import com.example.sequencerecord.score.ScoreFragmentViewModel
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class UserApplication : Application(){




    override fun onCreate() {
        super.onCreate()

    }
}