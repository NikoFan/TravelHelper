package com.example.travelhelper.VIEW_MODEL

import android.app.Application
import android.content.Context
import com.google.gson.JsonObject
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.travelhelper.GitHubJsonApi.App
import com.example.travelhelper.GitHubJsonApi.AppDatabase
import com.example.travelhelper.GitHubJsonApi.GitHubApiClient
import com.example.travelhelper.GitHubJsonApi.JsonCache
import com.example.travelhelper.MODEL.AdviceModel
import com.example.travelhelper.MODEL.Topics
import com.example.travelhelper.R
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okio.IOException
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

            // 1. Загружаем с GitHub
            val response = GitHubApiClient.api.getRawJson()
            println("response\n\n")
            println(response)


            val json = response
            println(json)

            // 2. Сохраняем в Room

            saveJson(
                context = context,
                json = json
            )


        } catch (e: IOError) {
            println("JSON Update failed")
        }
    }

    fun TakeJson(
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
        val db = AppDatabase.getInstance(context)
        val cache = JsonCache(
            jsonString = json)
        viewModelScope.launch {
            println(db.jsonCacheDao().saveCache(cache = cache))
        }
        var text = ""
        viewModelScope.launch {
            println(db.jsonCacheDao().getCache())
            text = db.jsonCacheDao().getCache().toString()
        }
        println("\n\n\n\n\n\n\n")
        println(text)
    }


    // Метод для чтения JSON файла
    fun ReadJson(
        context: Context
    ) {
        try {
            println("DElete data to check Upload Function")

            val db = AppDatabase.getInstance(context)
            var jsonText = ""

            viewModelScope.launch {
                db.jsonCacheDao().clearAllData()


            }
            println("Data was deleted! See all data what exist\n\n\n")
            viewModelScope.launch {

                jsonText =  db.jsonCacheDao().getCache().toString()
            }
            println(jsonText)
            println(jsonText.isEmpty())
            if (jsonText.isEmpty()){
                ReadStaticStaticJsonFile(context)
            }

        } catch (e: IOException) {
            ReadStaticStaticJsonFile(context)
        }


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

        println(themes)
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