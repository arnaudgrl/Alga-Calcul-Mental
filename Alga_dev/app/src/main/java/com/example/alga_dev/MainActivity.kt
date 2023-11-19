package com.example.alga_dev

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.alga_dev.gamemenu.GameMenu
import com.example.alga_dev.mainpage.MainPage
import com.example.alga_dev.settingspage.SettingsPage
import com.example.alga_dev.ui.theme.Alga_devTheme


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
                }
            }
        }
    }
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
        goToMainFromMenu = { navController.navigate("mainPage") }
    )

}

@Composable
fun AtSettingsPage(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue),
        contentAlignment = Alignment.Center
    ) {
        SettingsPage(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Blue),
            goToMainFromSettings = { navController.navigate("mainPage") }
        )
    }
}









