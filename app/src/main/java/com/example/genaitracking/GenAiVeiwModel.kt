package com.example.genaitracking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.genaitracking.Model.GraphModel
import com.example.genaitracking.Model.TextModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class GenAiVeiwModel : ViewModel() {
    var TextState= MutableStateFlow(TextState())

    var GraphState=MutableStateFlow(GraphState())
    fun requestText(Url : String , question : String){
        if("visualise" in question || "Visualise" in question){
            GraphState.value=GraphState(Loading = true)
            try{
                viewModelScope.launch { val response= KtorClient.requestGraph(Url,question)
                GraphState.value=GraphState(Loading = false,data =response as GraphModel)}

            }catch(e:Exception){
                GraphState.value=GraphState(Loading = false,error = e.message)

            }
        }
        else{
            TextState.value=TextState(Loading = true)

            try{
                viewModelScope.launch {
                    //delay()
                    val response= KtorClient.requestText(Url,question)
                TextState.value=TextState(Loading = false,data =response as TextModel)}

            }catch(e:Exception){
                TextState.value=TextState(Loading = false,error = e.message)
        }


        }

    }
}

data class TextState(
    var Loading: Boolean= false,
    var data: TextModel?=null,
    var error:String?=null
)
data class GraphState(
    var Loading: Boolean= false,
    var data: GraphModel?=null,
    var error:String?=null
)
