// GameScreen.kt
package com.example.hector_new

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.style.TextAlign
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@Composable
fun GameScreen(points: MutableState<Int>, navController: NavController) {
    var randomNumber by remember { mutableStateOf("") }
    var validExpression by remember { mutableStateOf("") }
    var userExpression by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("") }
    var showNewGameButton by remember { mutableStateOf(false) }
    var checkButtonEnabled by remember { mutableStateOf(true) }
    var showHomeButton by remember { mutableStateOf(false) }
    var showPointsAddedDialog by remember { mutableStateOf(false) }

    var startTime by remember { mutableStateOf(0L) }
    var timerTime by remember { mutableStateOf(0L) }
    var formattedTime by remember { mutableStateOf("00:00") }

    fun generateNewPuzzle() {
        val (number, expression) = generateValidNumber()
        randomNumber = number
        validExpression = expression
        userExpression = ""
        resultText = ""
        showNewGameButton = false
        checkButtonEnabled = true
        showHomeButton = false
        startTime = System.currentTimeMillis()
        timerTime = 0L
    }

    LaunchedEffect(Unit) {
        generateNewPuzzle()
    }

    LaunchedEffect(startTime, checkButtonEnabled) {
        while (checkButtonEnabled) {
            val currentTime = System.currentTimeMillis()
            val elapsedTime = currentTime - startTime
            timerTime = elapsedTime
            val minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime)
            val seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime) % 60
            formattedTime = String.format("%02d:%02d", minutes, seconds)
            delay(1000L)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .border(2.dp, Color.LightGray, RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .background(Color.White.copy(alpha = 0.8f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Time: $formattedTime",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Number: $randomNumber",
                style = TextStyle(fontSize = 36.sp, fontWeight = FontWeight.Bold)
            )
            Box(
                modifier = Modifier
                    .border(2.dp, Color.LightGray, RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Column(horizontalAlignment = Alignment.Start) {
                    Text(
                        text = "Game Rules:",
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "• Sequence: Use six given digits (1-9) in order, no rearranging.",
                        style = TextStyle(fontSize = 16.sp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "• Goal: Insert operations to make the expression equal 100.",
                        style = TextStyle(fontSize = 16.sp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "• Operations: Use +, -, ×, ÷, ^, and parentheses.",
                        style = TextStyle(fontSize = 16.sp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "• Validity: The equation must be mathematically correct and equal 100.",
                        style = TextStyle(fontSize = 16.sp)
                    )
                }
            }
            OutlinedTextField(
                value = userExpression,
                onValueChange = { userExpression = it },
                label = { Text("Enter your expression") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(0.8f),
                shape = RoundedCornerShape(8.dp),
                textStyle = TextStyle(fontSize = 20.sp)
            )
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(
                    onClick = {
                        if (isValidExpression(userExpression, randomNumber)) {
                            try {
                                val result = evaluate(userExpression)
                                if (result == 100.0) {
                                    val endTime = System.currentTimeMillis()
                                    val duration = endTime - startTime
                                    val minutes = TimeUnit.MILLISECONDS.toMinutes(duration)
                                    val seconds = TimeUnit.MILLISECONDS.toSeconds(duration) % 60
                                    val timeSpent = String.format("%02d:%02d", minutes, seconds)
                                    resultText = "Correct! Solved in $timeSpent."
                                    points.value += 10
                                    showPointsAddedDialog = true
                                    checkButtonEnabled = false

                                    GameData.gameRecords.add(
                                        GameRecord(
                                            randomNumber = randomNumber,
                                            userExpression = userExpression,
                                            timeTaken = timeSpent
                                        )
                                    )
                                } else {
                                    resultText = "Incorrect. Try again."
                                }
                            } catch (e: Exception) {
                                resultText = "Invalid expression: ${e.message}"
                            }
                        } else {
                            resultText = "Invalid input. Check digits and sequence."
                        }
                    },
                    enabled = checkButtonEnabled,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Check", fontSize = 18.sp)
                }
                Button(
                    onClick = {
                        resultText = "Answer: $validExpression"
                        showNewGameButton = true
                        checkButtonEnabled = false
                        showHomeButton = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Answer", fontSize = 18.sp)
                }
            }
            Text(
                text = resultText,
                style = TextStyle(fontSize = 22.sp, textAlign = TextAlign.Center),
                modifier = Modifier.padding(top = 24.dp)
            )
            if (showNewGameButton) {
                Button(onClick = { generateNewPuzzle() }) {
                    Text("New Game", fontSize = 18.sp)
                }
            }
            if (showHomeButton) {
                Button(onClick = { navController.popBackStack() }) {
                    Text("Home", fontSize = 18.sp)
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
    }
    if (showPointsAddedDialog) {
        AlertDialog(
            onDismissRequest = {
                showPointsAddedDialog = false
                generateNewPuzzle()
            },
            title = { Text("+10 Points!", fontSize = 20.sp) },
            text = {
                Column {
                    Text("Congratulations!", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    if (formattedTime.isNotEmpty()) {
                        Text("Time taken: $formattedTime", fontSize = 16.sp)
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    showPointsAddedDialog = false
                    generateNewPuzzle()
                }) {
                    Text("OK", fontSize = 18.sp)
                }
            }
        )
    }
}