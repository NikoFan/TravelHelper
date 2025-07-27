package com.example.travelhelper.VIEW

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    private fun UiConstructor(){
        Column (
            modifier = Modifier
                .fillMaxSize()
        ) {
            CreateScrollArea(
                vm = viewModel
            )
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
fun TopicCardConstructor(topic: Topics){
    Card (
        modifier = Modifier
            .fillMaxWidth()

            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(
                color = topic.topicSecondColor.toColorInt())
        )
    ){
        Box(
            modifier = Modifier
                .height(200.dp)
                .background(
                    color = Color(
                        color = topic.topicMainColor.toColorInt()
                    )
                )
                .fillMaxWidth(0.8f),

        ){
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
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
    vm: HomeScreenViewModel
) {
    vm.AddNewTopicInformation(
        topicInformation = Topics(
            topicId = 1,
            topicTitle = "Fire",
            topicImage = 12,
            topicMainColor = "#f3a180",
            topicSecondColor = "#cf0a13"

        )
    )
    vm.AddNewTopicInformation(
        topicInformation = Topics(
            topicId = 2,
            topicTitle = "Water",
            topicImage = 12,
            topicMainColor = "#7580fe",
            topicSecondColor = "#2c09b8"

        )
    )
    vm.AddNewTopicInformation(
        topicInformation = Topics(
            topicId = 3,
            topicTitle = "Weapon",
            topicImage = 12,
            topicMainColor = "#eec185",
            topicSecondColor = "#ae7913"

        )
    )
    val topics by vm.topicInformation
    println(topics)
    LazyColumn (
        modifier = Modifier
            .padding(20.dp)
            .fillMaxHeight(0.9f),
    ){
        items(
            topics
        ){topic ->
            TopicCardConstructor(
                topic = topic
            )

        }
    }

}


