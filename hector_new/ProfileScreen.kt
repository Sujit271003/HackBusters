// ProfileScreen.kt
package com.example.hector_new

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.util.concurrent.TimeUnit

@Composable
fun ProfileScreen(
    navController: NavController,
    userName: String,
    userPhoneNumber: String,
    points: Int,
    onLogout: () -> Unit
) {
    // Extract time data from GameData.gameRecords
    val timeStrings = GameData.gameRecords.map { it.timeTaken }

    // Convert time strings to milliseconds
    val timeMillis = remember(timeStrings) {
        timeStrings.map { timeString ->
            val parts = timeString.split(":")
            if (parts.size == 2) {
                val minutes = parts[0].toLongOrNull() ?: 0L
                val seconds = parts[1].toLongOrNull() ?: 0L
                TimeUnit.MINUTES.toMillis(minutes) + TimeUnit.SECONDS.toMillis(seconds)
            } else {
                0L // Invalid time string, default to 0
            }
        }
    }

    // Calculate time statistics
    val timeStats = remember(timeMillis) {
        TimeStatsCalculation.calculateTimeStats(timeMillis)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFE0E0E0))
                .padding(24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "Profile",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )

                ProfileInfoRow("Name", userName)
                ProfileInfoRow("Phone Number", userPhoneNumber)
                ProfileInfoRow("Points", points.toString())
                ProfileInfoRow("Best Time", timeStats.bestTime)
                ProfileInfoRow("Average Time", timeStats.averageTime)
                ProfileInfoRow("Worst Time", timeStats.worstTime)

                Text("Game History", style = MaterialTheme.typography.headlineSmall)

                // Table Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Number", fontWeight = FontWeight.Bold)
                    Text("Expression", fontWeight = FontWeight.Bold)
                    Text("Time", fontWeight = FontWeight.Bold)
                }
                Divider()

                // Scrollable Table Content
                LazyColumn(modifier = Modifier.heightIn(max = 200.dp)) {
                    items(GameData.gameRecords) { record ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(record.randomNumber)
                            Text(record.userExpression)
                            Text(record.timeTaken)
                        }
                        Divider()
                    }
                }

                Button(
                    onClick = { navController.popBackStack() },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF64B5F6))
                ) {
                    Text("Back to Home", color = Color.White)
                }

                Button(
                    onClick = {
                        GameData.gameRecords.clear() // Clear game history on logout
                        onLogout()
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("Logout", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun ProfileInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label:",
            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF555555))
        )
        Text(
            text = value,
            style = TextStyle(fontSize = 18.sp, color = Color(0xFF333333))
        )
    }
}