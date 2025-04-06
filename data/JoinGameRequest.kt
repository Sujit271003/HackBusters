package com.example.hector_new.data

import kotlinx.serialization.Serializable

@Serializable
data class JoinGameRequest(
    val gameId: String,
    val playerId: String
)