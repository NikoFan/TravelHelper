package com.example.travelhelper.VIEW

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.ViewModelProvider

import com.example.travelhelper.MODEL.Topics
import com.example.travelhelper.VIEW.ui.theme.TravelHelperTheme
import com.example.travelhelper.VIEW_MODEL.HomeScreenViewModel

class HomePageView : ComponentActivity() {
    private val viewModel by viewModels<HomeScreenViewModel> {
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TravelHelperTheme {
                MainScreen()
            }
        }
    }

    @Composable
    fun MainScreen() {
        val configuration = LocalConfiguration.current
        val isLandscape = configuration.screenWidthDp > configuration.screenHeightDp

        if (isLandscape) {
            LandscapeScreen(
                viewModel = viewModel)
        } else {
            PortraitScreen(
                viewModel = viewModel)
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun PreviewFunction() {
        TravelHelperTheme {
            MainScreen()
        }
    }


}


@Composable
fun PortraitScreen(
    viewModel: HomeScreenViewModel
) {
    // Состояние для языка с сохранением при повороте
    var currentLanguage by rememberSaveable { mutableStateOf("ru") }
    val context: Context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        CreateScrollAreaVertical(
            vm = viewModel,
            context = LocalContext.current
        )

        // Кнопки для портретного режима
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Кнопка обновления данных
            Button(
                onClick = {
                    Log.d("MyLog", "Обновление данных...")
                    viewModel.TakeJsonFromApiAndSaveIt(
                        context = context
                    )

                },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.width(210.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
            ) {
                Text("Обновить данные", fontSize = 18.sp)
            }

            // Кнопка переключения языка
            Button(
                onClick = {
                    currentLanguage = if (currentLanguage == "ru") "en" else "ru"
                    Log.d("MyLog", "Язык изменен на: $currentLanguage")
                },
                modifier = Modifier.size(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                contentPadding = PaddingValues(3.dp)
            ) {
                Text(
                    text = if (currentLanguage == "ru") "EN" else "RU",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun LandscapeScreen(viewModel: HomeScreenViewModel) {
    // Состояние для языка с сохранением при повороте
    var currentLanguage by rememberSaveable { mutableStateOf("ru") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        CreateScrollAreaHorizontal(
            vm = viewModel,
            context = LocalContext.current
        )
        // Кнопки для альбомного режима
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp, vertical = 15.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Кнопка обновления данных (альбомная версия)
            Button(
                onClick = { Log.d("MyLog", "Обновление данных (альбомный)...") },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.width(250.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
            ) {
                Text("Обновить данные", fontSize = 18.sp)
            }

            // Кнопка переключения языка (альбомная версия)
            Button(
                onClick = {
                    currentLanguage = if (currentLanguage == "ru") "en" else "ru"
                    Log.d("MyLog", "Язык изменен на: $currentLanguage")
                },
                modifier = Modifier
                    .width(80.dp)
                    .height(48.dp),
                contentPadding = PaddingValues(3.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
            ) {
                Text(
                    text = if (currentLanguage == "ru") "English" else "Русский",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun CreateScrollAreaVertical(vm: HomeScreenViewModel, context: Context) {
    vm.ReadJson(context = context)
    val topics by vm.topicInformation

    LazyColumn(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxHeight(0.9f),
    ) {
        items(topics) { topic ->
            TopicCardConstructor(
                topic = topic,
                cardHeightParam = 200
            )
        }
    }
}

@Composable
fun CreateScrollAreaHorizontal(vm: HomeScreenViewModel, context: Context) {
    vm.ReadJson(context = context)
    val topics by vm.topicInformation

    LazyRow(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxHeight(0.8f),
    ) {
        items(topics) { topic ->
            TopicCardConstructor(
                topic = topic,
                cardHeightParam = 200
            )
        }
    }
}

@Composable
fun TopicCardConstructor(
    topic: Topics,
    cardHeightParam: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(color = topic.topicSecondColor.toColorInt())
        )
    ) {
        Box(
            modifier = Modifier
                .height(cardHeightParam.dp)
                .background(color = Color(color = topic.topicMainColor.toColorInt()))
                .fillMaxWidth(0.8f),
        ) {
            val context = LocalContext.current
            val resId = context.resources.getIdentifier(
                topic.topicImage,
                "drawable",
                context.packageName
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(0.65f),
                    painter = painterResource(resId),
                    contentDescription = "Topic Icon"
                )
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = topic.topicTitle,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.W500
                )
            }
        }
    }
}