package com.example.hector_new.data

import kotlinx.serialization.Serializable

@Serializable
data class AnswerData(val gameId: String, val playerId: String, val expression: String)