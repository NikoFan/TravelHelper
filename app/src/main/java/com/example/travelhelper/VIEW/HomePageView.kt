package com.example.travelhelper.VIEW

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import com.example.travelhelper.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.example.travelhelper.MODEL.Topics
import com.example.travelhelper.VIEW.ui.theme.TravelHelperTheme
import com.example.travelhelper.VIEW_MODEL.HomeScreenViewModel

class HomePageView : ComponentActivity() {
    private val viewModel = HomeScreenViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TravelHelperTheme {
                UiConstructor()
            }
        }
    }

    @Composable
    private fun UiConstructor() {

        // тут будет хранится текущий язык и при любых изменениях интершейс отреагирует
        var currentLanguage by remember { mutableStateOf("ru") }

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CreateScrollArea(
                vm = viewModel,
                context = LocalContext.current
            )



            //пока это почти все что я потрогал, выносить в отдельную функцию создание 2х кнопок я пока не вижу
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 28.dp)
                    .height(48.dp),
            )
            {
                Button(
                    onClick = {
                        Log.d(
                            "MyLog", "кнопка - Друг, тебе просто кнопку сделать," +
                                    " настроить кликабл, чтобы просто принтовал. Я потом сделаю " +
                                    "обнову - скажу переделать кликабл"
                        )
                    },
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Обновить данные",
                        fontSize = 18.sp)
                }
                Button(
                    onClick = {
                        currentLanguage = if (currentLanguage == "ru") "en" else "ru"
                        Log.d("MyLog", "теперь язык - $currentLanguage")
                    },
                    modifier = Modifier
                        .size(48.dp),
                    contentPadding = PaddingValues(3.dp)
                )

                {
                    Text(
                        //пока буковами, нет тз - результат хз
                        text = if (currentLanguage == "ru") "EN" else "RU",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        TravelHelperTheme {
            UiConstructor()

        }
    }
}


@Composable
fun TopicCardConstructor(topic: Topics) {
    Card(
        modifier = Modifier
            .fillMaxWidth()





            ///////////////////////////////////////////////////CЫН СТЕКЛОПАКЕТА
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(
                color = topic.topicSecondColor.toColorInt()
            )
        )
    ) {
        Box(
            modifier = Modifier
                .height(200.dp)
                .background(
                    color = Color(
                        color = topic.topicMainColor.toColorInt()
                    )
                )
                .fillMaxWidth(0.8f),

            ) {
            val context = LocalContext.current
            val resId = context.resources.getIdentifier(
                topic.topicImage,
                "drawable",
                context.packageName
            )
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxSize(0.65f),
                    painter = painterResource(resId),
                    contentDescription = "Topic Icon"
                )
                Text(
                    modifier = Modifier
                        .padding(10.dp),

                    text = topic.topicTitle,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.W500
                )
            }
        }
    }
}


@Composable
fun CreateScrollArea(
    vm: HomeScreenViewModel,
    context: Context
) {
    vm.ReadJson(
        context = context
    )
    val topics by vm.topicInformation
    println(topics)
    LazyColumn(
        modifier = Modifier







            ///////////////////////////////////////////////////CЫН СТЕКЛОПАКЕТА
            .padding(20.dp)
            .fillMaxHeight(0.9f),
    ) {
        items(
            topics
        ) { topic ->
            TopicCardConstructor(
                topic = topic
            )

        }
    }

}
