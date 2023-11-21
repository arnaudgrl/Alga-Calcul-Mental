package com.example.alga_dev

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.alga_dev.finalscorepage.FinalScorePage
import com.example.alga_dev.gamemenu.GameMenu
import com.example.alga_dev.mainpage.MainPage
import com.example.alga_dev.playpage.PlayPage
import com.example.alga_dev.settingspage.SettingsPage
import com.example.alga_dev.ui.theme.Alga_devTheme
import kotlinx.coroutines.delay
import java.text.DecimalFormat
import kotlin.random.Random


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Alga_devTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "mainPage") {
                    composable("mainPage") {
                        AtMainPage(navController = navController)
                    }
                    composable("settingsPage") {
                        AtSettingsPage(navController = navController)
                    }
                    composable("gameMenu") {
                        AtMenuPage(navController = navController)
                    }
                    composable("playGameBasic") {
                        PlayingGamePage(navController = navController, gameMode=GameMode.Basic)
                    }
                    composable("playGameAdvanced") {
                        PlayingGamePage(navController = navController, gameMode=GameMode.Advanced)
                    }
                    composable("playGamePercent") {
                        PlayingGamePage(navController = navController, gameMode=GameMode.Percent)
                    }
                    composable("finalScoreBasic") {
                        FinalScore(navController = navController, gameMode=GameMode.Basic)
                    }
                    composable("finalScoreAdvanced") {
                        FinalScore(navController = navController, gameMode=GameMode.Advanced)
                    }
                    composable("finalScorePercent") {
                        FinalScore(navController = navController, gameMode=GameMode.Percent)
                    }
                }
            }
        }
    }
}

@Composable
fun FinalScore(navController: NavController, gameMode: GameMode) {
    FinalScorePage(
        retryTapped = {
            when (gameMode) {
                GameMode.Basic -> {
                    navController.navigate("playGameBasic")
                }
                GameMode.Advanced -> {
                    navController.navigate("playGameAdvanced")
                }
                GameMode.Percent -> {
                    navController.navigate("playGamePercent")
                }
            }
        },
        returnToMenu = { navController.navigate("gameMenu") }
    )
}

@Composable
fun PlayingGamePage(navController: NavController, gameMode: GameMode) {
    var questionState by remember {
        mutableStateOf(generateRandomQuestion(gameMode))
    }
    var answersState by remember {
        mutableStateOf(generateRandomAnswers(questionState.answer))
    }

    var score by remember { mutableIntStateOf(0) }
    var timer by remember { mutableIntStateOf(5) }

    LaunchedEffect(timer) {
        while (timer>0) {
            delay(1000)
            timer--
        }
        when (gameMode) {
            GameMode.Basic -> {
                navController.navigate("finalScoreBasic")
            }
            GameMode.Advanced -> {
                navController.navigate("finalScoreAdvanced")
            }
            GameMode.Percent -> {
                navController.navigate("finalScorePercent")
            }
        }
    }

    PlayPage(
        goToMainFromGame = { navController.navigate("gameMenu") },
        questionTextContent = questionState.question,
        param1TextContent = answersState[0],
        param2TextContent = answersState[1],
        param3TextContent = answersState[2],
        param4TextContent = answersState[3],
        firstPropositionTapped = {
            if (answersState[0] == questionState.answer.toString()) {
                score++
            }
            questionState = generateRandomQuestion(gameMode)
            answersState = generateRandomAnswers(questionState.answer)
        },
        secondPropositionTapped = {
            if (answersState[1] == questionState.answer.toString()) {
                score++
            }
            questionState = generateRandomQuestion(gameMode)
            answersState = generateRandomAnswers(questionState.answer)
        },
        thirdPropositionTapped = {
            if (answersState[2] == questionState.answer.toString()) {
                score++
            }
            questionState = generateRandomQuestion(gameMode)
            answersState = generateRandomAnswers(questionState.answer)
        },
        fourthPropositionTapped = {
            if (answersState[3] == questionState.answer.toString()) {
                score++
            }
            questionState = generateRandomQuestion(gameMode)
            answersState = generateRandomAnswers(questionState.answer)
        },
        scoreTextContent = "$score",
        timeTextContent = formatTime(timer)
    )
}

sealed class GameMode {
    object Basic : GameMode()
    object Advanced : GameMode()
    object Percent : GameMode()
}
data class QuestionState(val question: String, val answer: Int)

fun generateRandomQuestion(gameMode: GameMode): QuestionState {
    return when (gameMode) {
        is GameMode.Basic -> generateBasicQuestion()
        is GameMode.Advanced -> generateAdvancedQuestion()
        is GameMode.Percent -> generatePercentQuestion()
    }
}

fun generateBasicQuestion(): QuestionState {
    val operand1 = Random.nextInt(1, 21)
    val operand2 = Random.nextInt(1, 21)
    val question = "$operand1 x $operand2"
    val answer = operand1 * operand2
    return QuestionState(question, answer)
}

fun generateAdvancedQuestion(): QuestionState {
    val operand1 = Random.nextInt(1, 21)
    val operand2 = Random.nextInt(1, 21)
    val question = "$operand1 / $operand2"
    val answer = operand1 / operand2
    return QuestionState(question, answer)
}

fun generatePercentQuestion(): QuestionState {
    val operand1 = Random.nextInt(1, 21)
    val operand2 = Random.nextInt(1, 21)
    val question = "$operand1 % $operand2"
    val answer = operand1 % operand2
    return QuestionState(question, answer)
}

fun generateRandomAnswers(correctAnswer: Int): List<String> {
    val incorrectAnswers = mutableListOf<Int>()
    while (incorrectAnswers.size < 3) {
        val randomIncorrectAnswer = Random.nextInt(1, 401)
        if (randomIncorrectAnswer != correctAnswer && !incorrectAnswers.contains(randomIncorrectAnswer)) {
            incorrectAnswers.add(randomIncorrectAnswer)
        }
    }
    val answers = mutableListOf<String>()
    answers.add(correctAnswer.toString())
    answers.addAll(incorrectAnswers.map { it.toString() })
    return answers.shuffled()
}

// Function to format time in MM:SS format
fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    val df = DecimalFormat("00")
    return "${df.format(minutes)}:${df.format(remainingSeconds)}"
}

@Composable
fun AtMainPage(navController: NavController) {
    MainPage(
        goToSettings = { navController.navigate("settingsPage") },
        goToStart = { navController.navigate("gameMenu") }
    )
}

@Composable
fun AtMenuPage(navController: NavController) {
    GameMenu(
        goToMainFromMenu = { navController.navigate("mainPage") },
        goToBasic = { navController.navigate("playGameBasic") },
        goToAdvanced = { navController.navigate("playGameAdvanced") },
        goToPercent = {  navController.navigate("playGamePercent") },
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)
    )
}

@Composable
fun AtSettingsPage(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue),
        contentAlignment = Alignment.TopCenter
    ) {
        SettingsPage(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Blue),
            goToMainFromSettings = { navController.navigate("mainPage") }
        )
    }
}