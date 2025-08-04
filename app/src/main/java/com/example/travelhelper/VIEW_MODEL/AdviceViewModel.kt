package com.example.travelhelper.VIEW_MODEL

import android.app.Application
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import com.example.travelhelper.GitHubJsonApi.AppDatabase
import com.example.travelhelper.GitHubJsonApi.JsonCache
import com.example.travelhelper.MODEL.AdviceModel
import com.example.travelhelper.R
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import okio.IOException

class AdviceViewModel(private val app: Application) : AndroidViewModel(app) {

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    // Список для хранения совевтов из JSON
    private val adviceListToSaveAdvicesFromJson = mutableStateListOf<AdviceModel>()

    var jsonText by mutableStateOf<JsonCache?>(null)
        private set

    // Список для возвращения информации на экран
    private val _advices = mutableStateOf<List<AdviceModel>>(emptyList())

    // Getter для передачи списка на экран
    val getAdviceObjects: State<List<AdviceModel>>
        get() = _advices



//    fun ReadDB(
//        context: Context
//    ){
//        println("READ DB")
//        if (readAccept) return
//        println("readAccept: $readAccept")
//        readAccept = true
//
//        val db = AppDatabase.getInstance(context)
//        viewModelScope.launch {
//            // Удаление данных, для проверки считывания
//            jsonText = db.jsonCacheDao().getCache().toString()
//        }
//    }

    suspend fun ReadDB(context: Context) {
        _isLoading.value = true
        val db = AppDatabase.getInstance(context)
        jsonText = db.jsonCacheDao().getCache()
        _isLoading.value = false
    }

    fun TakeCardInformation(
        context: Context
    ) {
        try {
            // ReadDB(context = context)
            println("jsonText ${jsonText?.jsonString}")
            println(InformationStorage.mainTopicName)
            println("NOT EXCEPTION")
//            println(jsonText == "")
//            println(jsonText == "null")
//            if (jsonText == "") {
//                ReadAdicesJsonFile(
//                    context = context
//                )
//            } else
            if (jsonText == null) {
                ReadAdicesJsonFile(
                    context = context
                )
            }
            else {

                ReadAdicesJsonFile(
                    context = context,
                    jsonString = jsonText?.jsonString
                )
            }

        } catch (e: IOException){
            println("EXCEPTION")
            ReadAdicesJsonFile(
                context = context
            )
        }
    }

    // Чтение JSON с советами
    private fun ReadAdicesJsonFile(
        context: Context,
        jsonString: String? = ""
    ) {
        if (jsonString == ""){
            println(1)
            val jsonString2 = context.resources.openRawResource(
                R.raw.advices_json_file
            )
                .bufferedReader()
                .use { it.readText() }
            println(2)
            val gson = Gson()
            val jsonObject = gson.fromJson(
                jsonString2,
                JsonObject::class.java
            )
            println(3)
            val themes = jsonObject.getAsJsonObject(InformationStorage.mainTopicName)

            // println(themes)
            themes.entrySet().map { entry ->
                val key = entry.key.toInt()
                println("KEY: $key")
                val value = entry.value.asJsonObject

                // Добавление объектов модели Topics в список
                val model = AdviceModel(
                    adviceIdNumber = key,
                    adviceTitle = value.get("Название").asString,
                    adviceBodyText = value.get("Текст").asString,
                    adviceModeDifficulty = value.get("Сложность").asString,
                )
                if (!adviceListToSaveAdvicesFromJson.contains(model)) {
                    AddAdviceToList(
                        adviceModel = model
                    )
                }


            }
        }else {
            val gson = Gson()
            val jsonObject = gson.fromJson(
                jsonString,
                JsonObject::class.java
            )
            val themes = jsonObject.getAsJsonObject(InformationStorage.mainTopicName)
            // println(themes)
            themes.entrySet().map { entry ->
                val key = entry.key.toInt()
                val value = entry.value.asJsonObject

                // Добавление объектов модели Topics в список
                val model = AdviceModel(
                    adviceIdNumber = key,
                    adviceTitle = value.get("Название").asString,
                    adviceBodyText = value.get("Текст").asString,
                    adviceModeDifficulty = value.get("Сложность").asString,
                )
                if (!adviceListToSaveAdvicesFromJson.contains(model)) {
                    AddAdviceToList(
                        adviceModel = model
                    )
                }

            }


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