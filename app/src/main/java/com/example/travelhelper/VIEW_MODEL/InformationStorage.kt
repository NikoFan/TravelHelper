package com.example.travelhelper.VIEW_MODEL

// Объект-хранилище для статической информации
object InformationStorage {
    // Получение и возвращение наименования выбранной тематики
    private var chooseTopicName: String = ""
    var mainTopicName: String
        set(value) {
            chooseTopicName = value

        }
        get() = chooseTopicName
}