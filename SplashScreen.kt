// SplashScreen.kt
package com.example.hector_new

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import kotlinx.coroutines.delay
//@Preview(showBackground = true)
@Composable
fun SplashScreen(navController: NavController) {
    val animationUrl = "https://lottie.host/ade9b28d-d933-487e-95ca-6a1c209953f1/pP1lnMPxEL.lottie"
    val composition by rememberLottieComposition(LottieCompositionSpec.Url(animationUrl))

    Surface(modifier = Modifier.fillMaxSize(), color = Color.Magenta) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "WELCOME !!",
                fontSize = 50.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.LightGray
            )
            Spacer(modifier = Modifier.height(19.dp))
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier.size(300.dp)
            )
            Spacer(modifier = Modifier.padding(12.dp))
            Text(text = "Designed By-\nSUJIT", color = Color.White)
        }
        LaunchedEffect(Unit) {
            delay(4000L) // 4-second delay
            navController.navigate("home") {
                popUpTo("splash") {
                    inclusive = true
                }
            }
        }
    }
}