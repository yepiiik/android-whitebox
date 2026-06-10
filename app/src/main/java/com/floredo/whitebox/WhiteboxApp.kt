package com.floredo.whitebox

import android.app.Application
import androidx.room.Room
import com.floredo.whitebox.data.local.AppDatabase

class WhiteboxApp : Application() {
    lateinit var database: AppDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "whitebox-db"
        ).build()
    }

    companion object {
        lateinit var instance: WhiteboxApp
            private set
    }
}
