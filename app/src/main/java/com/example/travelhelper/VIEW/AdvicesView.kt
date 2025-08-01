package com.example.travelhelper.VIEW

import android.content.Context
import android.content.Intent
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.room.util.TableInfo
import com.example.travelhelper.MODEL.Topics
import com.example.travelhelper.VIEW.ui.theme.TravelHelperTheme
import com.example.travelhelper.VIEW_MODEL.AdviceViewModel
import com.example.travelhelper.VIEW_MODEL.HomeScreenViewModel
import kotlin.getValue
import com.example.travelhelper.MODEL.AdviceModel


class AdvicesView : ComponentActivity() {
    private val viewModel by viewModels<AdviceViewModel> {
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TravelHelperTheme {
                CardsScreen()
            }
        }
    }


    @Composable
    fun CardsScreen() {
        val configuration = LocalConfiguration.current
        val isLandscape = configuration.screenWidthDp > configuration.screenHeightDp

        if (isLandscape) {
            LandscapeScreen(
                viewModel = viewModel
            )
        } else {
            PortraitScreen(
                viewModel = viewModel
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun PreviewFunction() {
        TravelHelperTheme {
            CardsScreen()
        }
    }
}

@Composable
fun PortraitScreen(
    viewModel: AdviceViewModel
) {
    // Состояние для языка с сохранением при повороте
    var currentLanguage by rememberSaveable { mutableStateOf("ru") }
    val context: Context = LocalContext.current

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ScrollAreaVertical(
            vm = viewModel,
            context = LocalContext.current
        )

        Button(
            onClick = {
                GoToMainScreen(
                    context = context
                )
            },
            ) {Text(text = "Назад")}

    }
}

@Composable
fun LandscapeScreen(viewModel: AdviceViewModel) {
        // Состояние для языка с сохранением при повороте
        var currentLanguage by rememberSaveable { mutableStateOf("ru") }
        val context: Context = LocalContext.current

        Row (
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    GoToMainScreen(
                        context = context
                    )
                }
            ) { Text(text = "Назад") }
        }

    ScrollAreaHorizontal(
        vm = viewModel,
        context = LocalContext.current
    )
}


@Composable
fun ScrollAreaVertical(vm: AdviceViewModel, context: Context) {
    //vm.ReadJson(context = context)
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
fun ScrollAreaHorizontal(vm: AdviceViewModel, context: Context) {
    //vm.ReadJson(context = context)
    val topics by vm.topicInformation

    LazyRow(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxHeight(0.8f),
    ) {
        items(topics) { topic ->
            TopicCardConstructor(
                topic = topic,
                cardHeightParam = 270
            )
        }
    }
}

@Composable
fun TopicCardConstructor(
    topic: Topics,
    cardHeightParam: Int
) {
    val context: Context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(color = topic.topicSecondColor.toColorInt())
        ),
        onClick = {
            GoToCard(
                context = context
            )
        }
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

fun GoToMainScreen(
context: Context
) {
    context.startActivity(
        Intent(
            context,
            HomePageView::class.java
        )
    )
}


data class AdviceModel(
    val adviceId: Int = 1,
    val adviceTitle: String = "Название 1",
    val adviceText: String = "Текст совета",
    val adviceDifficulty: String = "Сложный"
)
