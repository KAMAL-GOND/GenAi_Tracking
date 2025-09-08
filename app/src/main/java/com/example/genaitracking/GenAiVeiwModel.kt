package com.example.genaitracking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.genaitracking.Model.GraphModel
import com.example.genaitracking.Model.TextModel
import com.example.genaitracking.NewModels.NewText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class GenAiVeiwModel : ViewModel() {
    var textState = MutableStateFlow(TextState()) // Renamed for convention
    var graphState = MutableStateFlow(GraphState()) // Renamed for convention

    fun requestText(Url: String, question: String) {
        if ("visualise" in question.lowercase() || "visualize" in question.lowercase()) { // Added visualize and lowercase for broader match
            graphState.value = GraphState(Loading = true)
            viewModelScope.launch {
                try {
                    val response = KtorClient.requestGraph(Url, question)
                    if (response != null) {
                        graphState.value = GraphState(Loading = false, data = response)
                    } else {
                        graphState.value = GraphState(Loading = false, error = "Failed to fetch graph data or an error occurred.")
                    }
                } catch (e: Exception) { // Catching any unexpected exceptions during the launch block itself
                    graphState.value = GraphState(Loading = false, error = e.message ?: "An unknown error occurred.")
                }
            }
        } else {
            textState.value = TextState(Loading = true)
            viewModelScope.launch {
                try {
                    val response = KtorClient.requestText(Url, question)
                    if (response != null) {
                        textState.value = TextState(Loading = false, data = response)
                    } else {
                        // If KtorClient.requestText returns null, it means an error occurred.
                        // You might want to get the specific error from KtorClient if you made it return TextModel with error string.
                        textState.value = TextState(Loading = false, error = "Failed to fetch text data or an error occurred.")
                    }
                } catch (e: Exception) { // Catching any unexpected exceptions during the launch block itself
                    textState.value = TextState(Loading = false, error = e.message ?: "An unknown error occurred.")
                }
            }
        }
    }

    fun ClearState(){
        textState.value = TextState()
        graphState.value = GraphState()
    }
}

data class TextState(
    var Loading: Boolean = false,
    var data: NewText? = null,
    var error: String? = null
)

data class GraphState(
    var Loading: Boolean = false,
    var data: GraphModel? = null,
    var error: String? = null
)
