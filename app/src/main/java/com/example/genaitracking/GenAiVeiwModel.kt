package com.example.genaitracking

import androidx.lifecycle.ViewModel

class GenAiVeiwModel : ViewModel() {
}

data class State(
    var Loading: Boolean= false,
    var data:Any?,
    var error:String?
)