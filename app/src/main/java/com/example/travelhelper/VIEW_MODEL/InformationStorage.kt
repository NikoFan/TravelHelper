package com.example.travelhelper.VIEW_MODEL

// Объект-хранилище для статической информации
object InformationStorage {
    // Получение и возвращение наименования выбранной тематики

    var mainTopicName: String?
        set(value) {
            if (value != null){
                mainTopicName = value
            }
        }
        get() = mainTopicName
}