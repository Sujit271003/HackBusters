// HomeScreen.kt
package com.example.hector_new

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun HomeScreen(navController: NavController, points: MutableState<Int>, userName: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF6A1B9A), Color(0xFF4A148C))
                )
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
        ) {
            // Top Content
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { /* Navigate to profile */ }) {
                        Image(
                            painter = painterResource(id = R.drawable.profile_logo),
                            contentDescription = "Profile",
                            modifier = Modifier.size(48.dp)
                        )
                    }

                    IconButton(onClick = { /* Navigate to leaderboard */ }) {
                        Image(
                            painter = painterResource(id = R.drawable.leaderboard_logo),
                            contentDescription = "Leaderboard",
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }

                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "App Icon",
                    modifier = Modifier.size(120.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp)) // Reduced space
                Text(
                    text = "Welcome, $userName!", // Display the user's name
                    style = TextStyle(
                        fontSize = 24.sp, // Slightly smaller welcome text
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "Number Puzzle",
                    style = TextStyle(
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(48.dp))
                Button(
                    onClick = { navController.navigate("game") },
                    modifier = Modifier.padding(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFBB86FC)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Start Game", color = Color.Black)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { navController.navigate("rules") },
                    modifier = Modifier.padding(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFBB86FC)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Rules", color = Color.Black)
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Total Points: ${points.value}",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                )
            }

            // Bottom Image (Smaller)
            Image(
                painter = painterResource(id = R.drawable.bottom_image),
                contentDescription = "Bottom Image",
                modifier = Modifier
                    .size(300.dp)
//                contentScale = ContentScale.Fit
            )
        }
    }
}