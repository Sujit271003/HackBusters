// LoginScreen.kt
package com.example.hector_new

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun LoginScreen(onLogin: (String, String) -> Unit) {
    var username by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.login_bakcground), // Replace with your image resource
            contentDescription = "Login Background",
            contentScale = ContentScale.Crop, // Or Fit, FillBounds, etc.
            modifier = Modifier.fillMaxSize()
        )

        // Semi-transparent overlay for better text readability (optional)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f)) // Adjust alpha for darkness
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(32.dp)
                .background(Color.White.copy(alpha = 0.8f), RoundedCornerShape(16.dp))
                .padding(24.dp)
                .align(Alignment.Center) // Center the login form
        ) {
            // App Logo
            Image(
                painter = painterResource(id = R.drawable.hecto), // Replace with your actual logo
                contentDescription = "App Logo",
                modifier = Modifier.size(120.dp)
            )
            Text(
                "LOGIN",
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFF29323C),
                fontWeight = FontWeight.Bold
            )
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("User_name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Mobile number") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            )
            if (errorMessage.isNotEmpty()) {
                Text(errorMessage, color = MaterialTheme.colorScheme.error)
            }
            Button(
                onClick = {
                    if (username.isNotBlank() && isValidPhoneNumber(phoneNumber)) {
                        onLogin(username, phoneNumber)
                    } else {
                        errorMessage = if (username.isBlank()) "Name cannot be empty." else "Invalid phone number."
                    }
                },
                enabled = true,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8000FF), contentColor = Color.White)
            ) {
                Text("Log In", style = TextStyle(fontWeight = FontWeight.Bold))
            }
        }
    }
}

fun isValidPhoneNumber(phoneNumber: String): Boolean {
    return phoneNumber.length == 10 && phoneNumber.all { it.isDigit() }
}