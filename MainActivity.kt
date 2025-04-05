// MainActivity.kt
package com.example.hector_new

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hector_new.ui.theme.Hector_newTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Hector_newTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    NumberPuzzleApp()
                }
            }
        }
    }
}

@Composable
fun NumberPuzzleApp() {
    val navController = rememberNavController()
    val points = remember { mutableStateOf(0) }
    val userName = remember { mutableStateOf("User") } // Default user name

    NavHost(navController = navController, startDestination = "splash") { // Start at the splash screen
        composable("splash") { SplashScreen(navController) }
        composable("home") { HomeScreen(navController, points, userName.value) } // Pass the default user name
        composable("game") { GameScreen(points, navController) }
        composable("rules") { RulesScreen(navController) }
    }
}