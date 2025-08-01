package com.example.travelhelper.GitHubJsonApi

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface JsonCacheDao {


    @Query("DELETE FROM json_cache")
    suspend fun clearAllData()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCache(cache: JsonCache)

    @Query("SELECT * FROM json_cache WHERE id = 1")
    suspend fun getCache(): JsonCache?
}