package com.example.travelhelper.VIEW_MODEL

import android.app.Application
import android.content.Context
import com.google.gson.JsonObject
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelhelper.GitHubJsonApi.AppDatabase
import com.example.travelhelper.GitHubJsonApi.GitHubApiClient
import com.example.travelhelper.GitHubJsonApi.JsonCache
import com.example.travelhelper.MODEL.Topics
import com.example.travelhelper.R
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.io.IOError
import kotlin.toString

class HomeScreenViewModel(private val app: Application) : AndroidViewModel(app) {

    private var listOfTopics = mutableListOf<Topics>()
    private val _topics = mutableStateOf<List<Topics>>(emptyList())

    // Получение тем
    public val topicInformation: State<List<Topics>> = _topics
    suspend fun updateTips(
        context: Context
    ) {
        try {

            // Получение JSON текста с API gh-pages
            val response = GitHubApiClient.api.getRawJson()

            // Сохранение JSON строки в Room
            saveJson(
                context = context,
                json = response
            )




        } catch (e: Exception) {
            println("JSON Update failed")
        }
    }

    fun TakeJsonFromApiAndSaveIt(
        context: Context
    ) {
        viewModelScope.launch {
            updateTips(context = context)
        }
    }

    // Сохранение JSON в ROOm
    fun saveJson(
        context: Context,
        json: String
    ) {
        // Инициализация БД
        val db = AppDatabase.getInstance(context)
        // Получение кеша для записи
        val cache = JsonCache(
            jsonString = json
        )
        viewModelScope.launch {
            // Добавление кеша в local room
            db.jsonCacheDao().saveCache(cache = cache)
        }
        viewModelScope.launch {
            println("CACHE: ${db.jsonCacheDao().getCache()}")
        }

    }


    // Метод для чтения JSON файла
    fun ReadJson(
        context: Context
    ) {
        ReadStaticStaticJsonFile(context)
    }


    fun ReadStaticStaticJsonFile(
        context: Context
    ) {
        // Прочитка JSON файла в начале работы приложения
        val jsonString = context.resources.openRawResource(
            R.raw.topics_json_file
        )
            .bufferedReader()
            .use { it.readText() }
        val gson = Gson()
        val jsonObject = gson.fromJson(
            jsonString,
            JsonObject::class.java
        )
        val themes = jsonObject.getAsJsonObject("Темы")

        println("themes: $themes")
        themes.entrySet().map { entry ->
            val key = entry.key.toInt()
            val value = entry.value.asJsonObject

            // Добавление объектов модели Topics в список
            AddNewTopicInformation(
                topicInformation = Topics(
                    topicId = key,
                    topicTitle = value.get("Название").asString,
                    topicImage = value.get("Иконка").asString,
                    topicMainColor = value.get("Основной цвет").asString,
                    topicSecondColor = value.get("Вторичный цвет").asString,
                    topicGroup = value.get("Группа").asString
                )
            )
        }
    }


    // Метод добавления объектов в список
    public fun AddNewTopicInformation(
        topicInformation: Topics
    ) {
        listOfTopics.add(topicInformation)
        _topics.value = listOfTopics
    }


}