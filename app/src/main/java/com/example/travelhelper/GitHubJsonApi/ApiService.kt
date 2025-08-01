package com.example.travelhelper.GitHubJsonApi

import com.example.travelhelper.MODEL.AdviceModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.converter.scalars.ScalarsConverterFactory

object GitHubApiClient {
    private const val BASE_URL = "https://nikofan.github.io/AndroidJsonAPI/"

    // Интерфейс API с обработкой ошибок на низком уровне
    interface ApiService {
        @GET("advices_json_file.json")
        suspend fun getRawJson(): String // Получаем чистый текст без Response обертки
    }

    // Настраиваем Retrofit для работы с сырым текстом
    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.HEADERS
        })
        .addInterceptor { chain ->
            try {
                chain.proceed(chain.request())
            } catch (e: Exception) {
                throw ApiException("Network error: ${e.message}")
            }
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(ScalarsConverterFactory.create()) // Важно!
        .build()

    val api: ApiService = retrofit.create(ApiService::class.java)

    class ApiException(message: String) : Exception(message)
}