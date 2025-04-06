package com.example.hector_new.data

import kotlinx.serialization.Serializable

@Serializable
data class ReceivedAnswerData(val playerId: String, val expression: String)