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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.travelhelper.VIEW.ui.theme.TravelHelperTheme
import com.example.travelhelper.VIEW_MODEL.AdviceViewModel
import com.example.travelhelper.MODEL.AdviceModel
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
    val context: Context = LocalContext.current

    // Тестовое добавление данных в карточки
    viewModel.AddAdviceToList(
        adviceModel = AdviceModel(
            adviceIdNumber = 1,
            adviceTitle = "Название 1",
            adviceBodyText = "Текст для первого названия",
            adviceModeDifficulty = "Легко"
        )
    )
    viewModel.AddAdviceToList(
        adviceModel = AdviceModel(
            adviceIdNumber = 2,
            adviceTitle = "Название 2",
            adviceBodyText = "Текст для первого названия",
            adviceModeDifficulty = "Средняя"
        )
    )
    viewModel.AddAdviceToList(
        adviceModel = AdviceModel(
            adviceIdNumber = 3,
            adviceTitle = "Название 3 длинное специально для проверки переноса",
            adviceBodyText = "Текст для первого названия",
            adviceModeDifficulty = "Сложно"
        )
    )

    Column(
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
        ) { Text(text = "Назад") }

    }
}

@Composable
fun LandscapeScreen(viewModel: AdviceViewModel) {
    // Состояние для языка с сохранением при повороте
    var currentLanguage by rememberSaveable { mutableStateOf("ru") }
    val context: Context = LocalContext.current

    Row(
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
fun ScrollAreaVertical(
    vm: AdviceViewModel,
    context: Context
) {
    //vm.ReadJson(context = context)
    val advicesList by vm.getAdviceObjects

    LazyColumn(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxHeight(0.9f),
    ) {
        items(advicesList) { adviceModelObject ->
            AdviceCardConstructor(
                advice = adviceModelObject,
                cardHeightParam = 150
            )
        }
    }
}

@Composable
fun ScrollAreaHorizontal(
    vm: AdviceViewModel,
    context: Context
) {
    //vm.ReadJson(context = context)
    val advicesList by vm.getAdviceObjects

    LazyRow(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxHeight(0.8f),
    ) {
        items(advicesList) { adviceModelObject ->
            AdviceCardConstructor(
                advice = adviceModelObject,
                cardHeightParam = 200
            )
        }
    }
}

@Composable
fun AdviceCardConstructor(
    advice: AdviceModel,
    cardHeightParam: Int
) {
    val context: Context = LocalContext.current

    val adviceCardBackground = when {
        advice.adviceModeDifficulty == "Легко" -> Color.Green
        advice.adviceModeDifficulty == "Средняя" -> Color.Yellow
        advice.adviceModeDifficulty == "Сложно" -> Color.Red
        else -> Color.LightGray
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = adviceCardBackground
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
                .fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(10.dp),
                    text = advice.adviceTitle,
                    softWrap = true,
                    maxLines = 3,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.W500
                )

                Text(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(horizontal = 10.dp),
                    text = advice.adviceBodyText,
                    fontSize = 18.sp
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