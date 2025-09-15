package com.example.liftium

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.liftium.navigation.NavigationHost
import com.example.liftium.ui.theme.LiftiumTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LiftiumTheme {
                LiftiumApp()
            }
        }
    }
}

@Composable
fun LiftiumApp() {
    NavigationHost()
}

@Preview(showBackground = true)
@Composable
fun LiftiumAppPreview() {
    LiftiumTheme {
        LiftiumApp()
    }
}