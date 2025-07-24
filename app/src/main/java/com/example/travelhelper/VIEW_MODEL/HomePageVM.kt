package com.example.travelhelper.VIEW_MODEL

import androidx.lifecycle.ViewModel
import com.example.travelhelper.MODEL.HomePageModel

class HomePageVM : ViewModel() {
    private var selectedTopicName = HomePageModel().selectedTopic

    /*
    * Установка наименования выбранной темы
    * Возврат текущего наименования выбранной темы
    */
    public var setSelectedTopicName : String?
        set(value) {
            selectedTopicName = value
        }
        get() = selectedTopicName
}