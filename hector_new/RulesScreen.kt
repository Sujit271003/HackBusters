// RulesScreen.kt
package com.example.hector_new

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun RulesScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0)) // Light background
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Game Rules",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Boxy Rules Section
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(16.dp)
        ) {
            RuleBox("Sequence", "Use six given digits (1-9) in order, no rearranging.")
            Spacer(modifier = Modifier.height(12.dp))
            RuleBox("Goal", "Insert operations to make the expression equal 100.")
            Spacer(modifier = Modifier.height(12.dp))
            RuleBox("Operations", "Use +, -, ×, ÷, ^, and parentheses.")
            Spacer(modifier = Modifier.height(12.dp))
            RuleBox("Validity", "Equation must be mathematically correct and equal 100.")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth(0.8f),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Back to Game", fontSize = 18.sp)
        }
    }
}

@Composable
fun RuleBox(title: String, description: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = title,
            style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
            color = Color(0xFF333333)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            style = TextStyle(fontSize = 16.sp),
            color = Color(0xFF555555)
        )
    }
}