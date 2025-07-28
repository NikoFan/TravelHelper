package com.example.travelhelper.MODEL
import android.graphics.drawable.BitmapDrawable
import com.example.travelhelper.R
// Модель основныз тем
data class Topics (
    val topicId: Int,
    val topicTitle: String,
    val topicImage: String,
    val topicMainColor: String,
    val topicSecondColor: String,
    val topicGroup: String
)
