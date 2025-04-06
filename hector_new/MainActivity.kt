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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hector_new.ui.theme.Hector_newTheme
import kotlinx.coroutines.launch

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
    val userName = remember { mutableStateOf("User") }
    val loggedIn = remember { mutableStateOf(false) }
    val userPhoneNumber = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope() // Important for coroutines

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("login") {
            LoginScreen(onLogin = { name, phoneNumber ->
                userName.value = name
                userPhoneNumber.value = phoneNumber
                loggedIn.value = true
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            })
        }
        composable("home") { HomeScreen(navController, points, userName.value, userPhoneNumber.value) }
        composable("game") { GameScreen(points, navController) }
        composable("rules") { RulesScreen(navController) }
        composable("profile") {
            ProfileScreen(
                navController,
                userName.value,
                userPhoneNumber.value,
                points.value,
                onLogout = {
                    scope.launch {
                        loggedIn.value = false
                        navController.navigate("login") {
                            popUpTo("home") { inclusive = true }
                        }
                    }
                }
            )
        }
    }
}