package com.example.travelhelper.GitHubJsonApi

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson

@Entity(tableName = "json_cache")
data class JsonCache(
    @PrimaryKey val id: Int = 1, // всегда одна запись
    val jsonString: String
){
    // Преобразование JSON в объект
    inline fun <reified T> fromJson(): T? {
        return try {
            Gson().fromJson(jsonString, T::class.java)
        } catch (e: Exception) {
            null
        }
    }

    // Создание из объекта
    companion object {
        inline fun <reified T> toJson(obj: T): JsonCache {
            return JsonCache(jsonString = Gson().toJson(obj))
        }
    }
}