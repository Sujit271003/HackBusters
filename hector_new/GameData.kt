// GameData.kt
package com.example.hector_new

object GameData {
    val gameRecords = mutableListOf<GameRecord>()
}

data class GameRecord(
    val randomNumber: String,
    val userExpression: String,
    val timeTaken: String
)