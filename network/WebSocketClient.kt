package com.example.hector_new.network

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class WebSocketClient(
    private val onOpen: (WebSocket) -> Unit,
    private val onMessage: (String) -> Unit,
    private val onClosing: (WebSocket, Int, String) -> Unit,
    private val onFailure: (Throwable) -> Unit
) {
    private var webSocket: WebSocket? = null
    private val client: OkHttpClient

    init {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }

    fun connect(url: String) {
        val request = Request.Builder()
            .url(url)
            .build()

        val listener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                this@WebSocketClient.webSocket = webSocket
                onOpen(webSocket)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                onMessage(text)
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                this@WebSocketClient.webSocket = null
                onClosing(webSocket, code, reason)
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                this@WebSocketClient.webSocket = null
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                this@WebSocketClient.webSocket = null
                onFailure(t)
            }
        }

        // Create the WebSocket connection
        webSocket = client.newWebSocket(request, listener)
    }

    fun send(message: String): Boolean {
        return webSocket?.send(message) ?: false
    }

    fun close(): Boolean {
        return webSocket?.close(1000, "Normal Closure") ?: false
    }

    // Optional: Add a method to check connection status
    fun isConnected(): Boolean {
        return webSocket != null
    }

    // Cleanup method
    fun shutdown() {
        client.dispatcher.executorService.shutdown()
    }
}