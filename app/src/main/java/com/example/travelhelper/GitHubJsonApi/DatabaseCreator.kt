package com.example.travelhelper.GitHubJsonApi

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [JsonCache::class],
    version = 1,
    exportSchema = false  // Отключить если не нужны миграции
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jsonCacheDao(): JsonCacheDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}