package com.example.alga_dev

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                }
            }
        }
    }
}

@Composable
fun AtMainPage(navController: NavController) {
    MainPage(goToSettings = { navController.navigate("settingsPage") })
}

@Composable
fun AtSettingsPage(navController: NavController) {
    SettingsPage(goToMainFromSettings = {navController.navigate("mainPage")})
}









