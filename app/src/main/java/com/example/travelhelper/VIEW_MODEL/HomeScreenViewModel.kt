package com.example.travelhelper.VIEW_MODEL

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.travelhelper.MODEL.Topics

class HomeScreenViewModel : ViewModel() {
    private var listOfTopics = mutableListOf<Topics>()
    private val _topics = mutableStateOf<List<Topics>>(emptyList())

    // Получение тем
    public val topicInformation: State<List<Topics>> = _topics

    /*
    * Установка наименования выбранной темы
    * Возврат текущего наименования выбранной темы
    */
    public fun AddNewTopicInformation(
        topicInformation: Topics
    ) {
        listOfTopics.add(topicInformation)
        _topics.value = listOfTopics
    }


}