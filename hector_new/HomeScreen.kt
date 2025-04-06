package com.example.hector_new

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun StyledButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer {
                shadowElevation = 4.dp.toPx()
                shape = RoundedCornerShape(8.dp) // Overall shape
                clip = true
            }
            .background(Brush.verticalGradient(colors = listOf(Color(0xFF0A1128), Color(0xFF1C2541)))) // Dark base
    ) {
        // Brighter top layer with glow
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp) // Height of the top layer
                .background(Brush.horizontalGradient(colors = listOf(Color(0xFF4B72B7), Color(0xFF64B5F6))))
        )

        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp), // Shift content down slightly
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.White
            ),
            elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp, 0.dp), // Remove default button elevation
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Text(
                text = text,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Composable
fun HomeScreen(
    navController: NavController,
    points: MutableState<Int>,
    value: String,
    value1: String
) {
    val color1 = remember { mutableStateOf(Color(0xFF1A237E)) } // Darker blue
    val color2 = remember { mutableStateOf(Color(0xFF3F51B5)) } // Slightly lighter blue

    val animatedColor1 by animateColorAsState(
        targetValue = color1.value,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2500, easing = FastOutSlowInEasing), // Faster
            repeatMode = RepeatMode.Reverse
        ), label = "color1Animation"
    )

    val animatedColor2 by animateColorAsState(
        targetValue = color2.value,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing), // Faster
            repeatMode = RepeatMode.Reverse
        ), label = "color2Animation"
    )

    LaunchedEffect(Unit) {
        while (true) {
            delay(2000) // Colors will change every 2 seconds (faster)
            color1.value = Color(Random.nextInt(100), Random.nextInt(150), 120 + Random.nextInt(136), 255) // Vary blues
            color2.value = Color(Random.nextInt(100) + 50, Random.nextInt(150) + 50, 150 + Random.nextInt(106), 255) // Vary lighter blues
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(animatedColor1, animatedColor2)
                )
            )
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Profile Icon and Text
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(60.dp) // Increased size
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.15f)) // Subtle background
                            .border(2.dp, Color.White.copy(alpha = 0.6f), CircleShape) // White border
                            .clickable { navController.navigate("profile") }
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.profile_logo),
                            contentDescription = "Profile",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )
                    }
                    Text(
                        text = "Profile",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                // Leaderboard Icon and Text
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(60.dp) // Increased size
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.15f)) // Subtle background
                            .border(2.dp, Color.White.copy(alpha = 0.6f), CircleShape) // White border
                            .clickable { /* Navigate to leaderboard */ }
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.leaderboard_logo),
                            contentDescription = "Leaderboard",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )
                    }
                    Text(
                        text = "Leaderboard",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp)) // Decreased gap here

            Image(
                painter = painterResource(id = R.drawable.hecto), // Replace with your image resource
                contentDescription = "Number Puzzle Logo",
                modifier = Modifier.size(300.dp), // Adjust size as needed
                contentScale = ContentScale.Fit // Or other appropriate ContentScale
            )

            Spacer(modifier = Modifier.height(8.dp)) // Add some space between logo and text

            // "THINK - PLAY - WIN" Text
            Text(
                text = "THINK - PLAY - WIN",
                style = TextStyle(
                    color = Color.White.copy(alpha = 0.8f), // Slightly transparent white
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )

            Spacer(modifier = Modifier.height(32.dp)) // Decreased gap before buttons

            StyledButton(
                onClick = { navController.navigate("game") },
                text = "PLAY",
                modifier = Modifier.padding(vertical = 8.dp) // Decreased vertical padding
            )
            StyledButton(
                onClick = { navController.navigate("rules") },
                text = "RULES",
                modifier = Modifier.padding(vertical = 8.dp) // Decreased vertical padding
            )

            Spacer(modifier = Modifier.height(24.dp)) // Decreased gap before Total Points
        }
    }
}