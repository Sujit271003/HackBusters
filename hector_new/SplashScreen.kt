// SplashScreen.kt
package com.example.hector_new

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    val animationUrl = "https://lottie.host/ade9b28d-d933-487e-95ca-6a1c209953f1/pP1lnMPxEL.lottie"
    val composition by rememberLottieComposition(LottieCompositionSpec.Url(animationUrl))

    // Animation for the Welcome Text
    val infiniteTransition = rememberInfiniteTransition(label = "welcomeTextAnimation")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "welcomeTextScale"
    )
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 700, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "welcomeTextAlpha"
    )

    // Using a built-in cursive font family as an example
    val welcomeFontFamily = FontFamily.SansSerif

    Surface(modifier = Modifier.fillMaxSize(), color = Color.Blue) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "WELCOME !!",
                fontSize = 50.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.LightGray,
                fontFamily = welcomeFontFamily, // Apply the custom font family
                textAlign = TextAlign.Center, // Optional: Center the text
                modifier = Modifier.graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    alpha = alpha
                )
            )
            Spacer(modifier = Modifier.height(16.dp)) // Add some space below Welcome text

            // Add your logo here
            Image(
                painter = painterResource(id = R.drawable.hecto), // Replace with your logo resource ID
                contentDescription = "App Logo",
                modifier = Modifier.size(250.dp), // Adjust size as needed
                contentScale = ContentScale.Fit // Or other appropriate ContentScale
            )

            Spacer(modifier = Modifier.height(19.dp))
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier.size(300.dp)
            )
            Spacer(modifier = Modifier.padding(12.dp))
            Text(
                text = "Designed By-\nHACKBUSTERS",
                color = Color.White,
                textAlign = TextAlign.Center // Optional: Center the text
            )
        }
        LaunchedEffect(Unit) {
            delay(4000L) // 4-second delay
            navController.navigate("login") { // Navigate to login instead of home
                popUpTo("splash") {
                    inclusive = true
                }
            }
        }
    }
}