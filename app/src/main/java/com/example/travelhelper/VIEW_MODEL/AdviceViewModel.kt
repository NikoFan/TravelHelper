package com.example.travelhelper.VIEW_MODEL

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.travelhelper.MODEL.AdviceModel
import com.example.travelhelper.R
import com.google.gson.Gson
import com.google.gson.JsonObject

class AdviceViewModel {
    // Список для хранения совевтов из JSON
    private val adviceListToSaveAdvicesFromJson = mutableStateListOf<AdviceModel>()

    // Список для возвращения информации на экран
    private val _advices = mutableStateOf<List<AdviceModel>>(emptyList())

    // Getter для передачи списка на экран
    public val getAdviceObjects: State<List<AdviceModel>>
        get() = _advices

    // Чтение JSON с советами
    public fun ReadAdicesJsonFile(
        context: Context
    ) {
        val jsonString = context.resources.openRawResource(
            R.raw.advices_json_file
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
            AddAdviceToList(
                adviceModel = AdviceModel(
                    adviceIdNumber = key,
                    adviceTitle = value.get("Название").asString,
                    adviceBodyText = value.get("Текст").asString,
                    adviceModeDifficulty = value.get("Сложность").asString,
                )
            )

        }
    }

    // Метод добавления советов в список
    public fun AddAdviceToList(
        adviceModel: AdviceModel
    ) {
        adviceListToSaveAdvicesFromJson.add(adviceModel)
        _advices.value = adviceListToSaveAdvicesFromJson
    }

}