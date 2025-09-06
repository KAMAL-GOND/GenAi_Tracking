package com.example.genaitracking.Model

data class Output(
    val message: String,
    val x_axis: List<String>,
    val y_axis: List<Double>
)