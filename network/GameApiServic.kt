// network/GameApiService.kt
package com.example.hector_new.network

import com.example.hector_new.data.CreateGameResponse
import com.example.hector_new.data.JoinGameRequest
import com.example.hector_new.data.JoinGameResponse
import com.example.hector_new.data.AnswerData
import retrofit2.http.Body
import retrofit2.http.POST

interface GameApiService {
    @POST("/api/createGame")
    suspend fun createGame(): CreateGameResponse

    @POST("/api/joinGame")
    suspend fun joinGame(@Body request: JoinGameRequest): JoinGameResponse

    @POST("/api/submitAnswer")
    suspend fun submitAnswer(@Body answerData: AnswerData): Unit // Or your SubmitAnswerResponse
}