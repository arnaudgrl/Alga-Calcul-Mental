package com.example.alga_dev

import android.os.Bundle
import androidx.compose.ui.Alignment
import androidx.compose.foundation.border
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Box
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import com.example.alga_dev.ui.theme.Alga_devTheme
import com.example.alga_dev.mainpage.MainPage


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Alga_devTheme {
                // A surface container using the 'background' color from the theme
                Box(
                    modifier = Modifier
                        .background(color = Color.Red)
                        .border(1.dp , Color.Blue),  // Border thickness and color
                    contentAlignment = Alignment.TopEnd
                ) {
                    MainPage()
                }
            }
        }
    }
}
