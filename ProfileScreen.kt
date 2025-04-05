// ProfileScreen.kt
package com.example.hector_new

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun ProfileScreen(navController: NavController, userName: String, userPhoneNumber: String, points: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Profile", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Name: $userName", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Phone Number: $userPhoneNumber", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Points: $points", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.popBackStack() }, shape = RoundedCornerShape(8.dp)) {
            Text("Back to Home")
        }
    }
}