package com.example.genaitracking

import android.util.Log
import com.example.genaitracking.Model.GraphModel
import com.example.genaitracking.Model.TextModel
import com.example.genaitracking.NewModels.NewText
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.client.request.parameter
// Ktor already handles expectSuccess, so bodyAsText for non-200 is not typical with it.
// import io.ktor.client.statement.bodyAsText 
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object KtorClient {
    val httpClient = HttpClient(CIO) {
        expectSuccess = true // This means non-2xx responses will throw exceptions
        install(ContentNegotiation) {
            json(json = Json { ignoreUnknownKeys = true
                isLenient = true})
        }
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 600000
            connectTimeoutMillis = 600000
            socketTimeoutMillis = 600000
        }
    }

    suspend fun requestText(Url: String, question: String): NewText? {
        return try {
            val response = httpClient.get("https://${Url}.ngrok-free.app/response") {
                parameter("query", question)
            }

            response.body<NewText>()
        } catch (e: Exception) {
            Log.e("KtorClient", "requestText failed: ${e.message}")

            null
        }
    }

    suspend fun requestGraph(Url: String, question: String): GraphModel? {
        return try {
            val response = httpClient.get("https://${Url}.ngrok-free.app/response") {
                parameter("query", question)
            }
            // If expectSuccess = true, Ktor deserializes directly or throws.
            // Assuming your server returns a JSON that matches GraphModel for success.
            response.body<GraphModel>()
        } catch (e: Exception) {
            Log.e("KtorClient", "requestGraph failed: ${e.message}")
            // Return null or a GraphModel with an error indicator if your model supports it
            null
        }
    }
}
