package com.example.sequencerecord.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [ScoreEntry::class], version = 1)
abstract class RoomAppDB : RoomDatabase() {

    abstract fun scoreDao(): ScoreDao

    companion object{
        @Volatile
        private var INSTANCE : RoomAppDB? = null

        fun getAppDatabase(context : Context): RoomAppDB? {
            if(INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext, RoomAppDB::class.java, "AppDB"
                    ).build()

                }
            }
            return INSTANCE!!
        }
    }
}