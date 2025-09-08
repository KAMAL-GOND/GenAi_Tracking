package com.example.genaitracking.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.genaitracking.GenAiVeiwModel
import io.ktor.websocket.Frame

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen (veiwModel: GenAiVeiwModel){
    var graphState=veiwModel.graphState.collectAsState()
    var textState=veiwModel.textState.collectAsState()
    var text="Gen_Ai_Tracking"
    var url=remember{ mutableStateOf("fd69c9bba687") }
    var question=remember{ mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Scaffold (
        topBar = {
            TopAppBar(
                title = {

                    Text(text)
                } ,
                navigationIcon = { Icon(imageVector = Icons.Default.Menu, contentDescription = null, modifier = Modifier.clickable(onClick = {expanded =!expanded}))  },
                actions = {  },
                scrollBehavior = null,


            )

//            OutlinedTextField(
//                value = url.value,
//                onValueChange = {url.value=it},
//                label = { Text("Url") },
//                modifier = Modifier.fillMaxWidth(),
//                enabled = fa
//            )
        },
        bottomBar = {
            BottomAppBar {
                OutlinedTextField(
                    value = question.value,
                    onValueChange = {question.value=it},
                    label = { Text("Question") },
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {Icon(imageVector = Icons.AutoMirrored.Default.Send, contentDescription = null, modifier = Modifier.clickable(onClick = {
                        veiwModel.ClearState()
                        veiwModel.requestText(url.value,question.value)}))  }
                )

            }
        },




    ){innerPadding->


        Column(modifier = Modifier.fillMaxSize(), Arrangement.Top){
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {

                //DropdownMenuItem(
                OutlinedTextField(
                    value = url.value,
                    onValueChange = {url.value=it},
                    label = { Text("Url") },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = true
                )
                //)
            }

        }
        when{
            textState.value.Loading || graphState.value.Loading -> Box(modifier = Modifier.fillMaxWidth().padding(innerPadding),contentAlignment = Alignment.Center){ CircularProgressIndicator() }
            textState.value.error != null -> Text(text = textState.value.error!!)
            graphState.value.error != null -> Text(text = graphState.value.error!!)
            textState.value.data != null -> LazyColumn(modifier = Modifier.fillMaxSize().padding(innerPadding)) { item{ Text(text = textState.value.data!!.output!!.message.toString())}}
            graphState.value.data != null ->Box(modifier = Modifier.fillMaxWidth().padding(innerPadding)){ BarChartFromOutput(outputData = graphState.value.data!!.output)}

        }





            }
//        when{
//            textState.value.Loading || graphState.value.Loading -> Box(modifier = Modifier.fillMaxWidth(),contentAlignment = Alignment.Center){ CircularProgressIndicator() }
//            textState.value.error != null -> Text(text = textState.value.error!!)
//            graphState.value.error != null -> Text(text = graphState.value.error!!)
//            textState.value.data != null -> Text(text = textState.value.data!!.output.toString())
//            graphState.value.data != null -> BarChartFromOutput(outputData = graphState.value.data!!.output)
//
//        }

    }

