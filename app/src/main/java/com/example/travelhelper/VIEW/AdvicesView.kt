package com.example.travelhelper.VIEW

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.travelhelper.VIEW.ui.theme.TravelHelperTheme
import com.example.travelhelper.VIEW_MODEL.AdviceViewModel
import com.example.travelhelper.MODEL.AdviceModel
import com.example.travelhelper.ui.theme.GreenCard
import com.example.travelhelper.ui.theme.RedCard
import com.example.travelhelper.ui.theme.YellowCard
import kotlin.getValue


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

        PortraitScreen(
            viewModel = viewModel
        )

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
    val context: Context = LocalContext.current
    var isReady by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.ReadDB(context)
        isReady = true
    }

    if (!isReady) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }  else {
        viewModel.TakeCardInformation(
            context = context
        )

        val advicesList by viewModel.getAdviceObjects

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ScrollAreaVertical(
                vm = viewModel,
                advicesList = advicesList
            )

            Button(
                onClick = {
                    GoToMainScreen(
                        context = context
                    )
                },
                shape = RectangleShape,
                modifier = Modifier.width(200.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
            ) { Text(text = "Назад", fontSize = 22.sp) }

        }
    }

}


@Composable
fun ScrollAreaVertical(
    vm: AdviceViewModel,
    advicesList: List<AdviceModel>
) {
    println(advicesList)
    println(advicesList.size)

    LazyColumn(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxHeight(0.9f),
    ) {
        items(advicesList) { adviceModelObject ->
            AdviceCardConstructor(
                advice = adviceModelObject,

                )
        }
    }
}


@Composable
fun AdviceCardConstructor(
    advice: AdviceModel
) {

    val adviceCardBackground = when {
        advice.adviceModeDifficulty == "Легкий" -> GreenCard
        advice.adviceModeDifficulty == "Средний" -> YellowCard
        advice.adviceModeDifficulty == "Сложный" -> RedCard
        else -> Color.LightGray
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = adviceCardBackground
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight() // Ключевая строка - автоматическая высота
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                //Текст названия
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(10.dp),
                    text = advice.adviceTitle,
                    softWrap = true,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W500
                )
                //Текст сложности
                Text(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 15.dp, end = 15.dp, top = 0.dp, bottom = 5.dp),
                    text = "Сложность: " + advice.adviceModeDifficulty,
                    fontSize = 18.sp
                )
                //Текст описания
                Text(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 10.dp),
                    text = advice.adviceBodyText,
                    fontSize = 16.sp
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