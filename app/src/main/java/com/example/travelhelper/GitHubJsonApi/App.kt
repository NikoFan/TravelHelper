package com.example.travelhelper.GitHubJsonApi

import android.app.Application
import androidx.room.Room

class App : Application() {

    // Инициализация базы данных (синглтон)
    companion object {
        lateinit var instance: App
            private set
    }

    lateinit var database: AppDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app-database"
        ).build()
    }
}