package com.example.genaitracking

import android.util.Log
import com.example.genaitracking.Model.GraphModel
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
import io.ktor.client.request.parameter
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
        try{
        val response =httpClient.get("https://${Url}.ngrok-free.app/response"){
            //parameter("model",model)
            parameter("query",question)
        }
        if(response.status.value!=200){
            return response.body<TextModel>()
        }
        else{
            return response.bodyAsText()
        }} catch (e:Exception){
            return TextModel(e.message.toString())

        }
       // Log.d("response",response.bodyAsText())


    }
    suspend fun requestGraph(Url : String , question : String):Any? {
        //Log.d("request","$model $question")
        try{
        val response =httpClient.get( "https://${Url}.ngrok-free.app/response"){
        }
        if(response.status.value!=200){
            return response.body<GraphModel>()
        }
        else{
            return response.bodyAsText()
        }} catch (e:Exception){
            return e.message

        }


       // Log.d("response",response.bodyAsText())
        //return response.body<GraphModel>()

    }
}