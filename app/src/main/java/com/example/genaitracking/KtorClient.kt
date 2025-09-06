package com.example.genaitracking

import android.util.Log
import com.example.genaitracking.Model.TextModel
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
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
//import java.util.logging.Logger

//import java.util.logging.Logger

object KtorClient {
    //val apiKey= Apikey
    val httpClient = HttpClient(CIO) {
        expectSuccess=true;
        install(ContentNegotiation){
            json(json= Json { ignoreUnknownKeys=true })
        }
        install(Logging){
            logger= Logger.SIMPLE
            level= LogLevel.ALL
        }
        install(HttpTimeout){
            requestTimeoutMillis=60000
            connectTimeoutMillis=60000
            socketTimeoutMillis=60000
        }

    }

    suspend fun requestText(Url : String , question : String):Any? {
        //Log.d("request","$model $question")
        val response =httpClient.get( Url ){
        }
        Log.d("response",response.bodyAsText())
        return response.body<TextModel>()

    }
    suspend fun requestGraph(Url : String , question : String):Any? {
        //Log.d("request","$model $question")
        val response =httpClient.get( Url ){
        }
        Log.d("response",response.bodyAsText())
        return response.body<TextModel>()

    }
}