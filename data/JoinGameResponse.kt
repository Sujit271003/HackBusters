package com.example.hector_new.data

import kotlinx.serialization.Serializable

@Serializable
data class JoinGameResponse(val success: Boolean, val message: String)
