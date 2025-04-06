package com.example.hector_new.data

import kotlinx.serialization.Serializable

@Serializable
data class StartGameData(val player1Id: String, val player2Id: String, val randomNumber: String)