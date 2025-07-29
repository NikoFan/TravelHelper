package com.example.travelhelper.VIEW_MODEL

import android.content.Context
import com.google.gson.JsonObject
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.travelhelper.MODEL.Topics
import com.example.travelhelper.R
import com.google.gson.Gson

class HomeScreenViewModel() : ViewModel() {
    private var listOfTopics = mutableListOf<Topics>()
    private val _topics = mutableStateOf<List<Topics>>(emptyList())

    // Получение тем
    public val topicInformation: State<List<Topics>> = _topics

    // Метод для чтения JSON файла
    fun ReadJson(
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