package com.example.genaitracking.Screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
//import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer

import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.entryOf

// Your data class
data class Output(
    val message: String,
    val x_axis: List<String>,
    val y_axis: List<Double>
)

@Composable
fun BarChartFromOutput(outputData: Output?) {
    // Return early if data is null or empty to prevent crashes
    if (outputData == null || outputData.x_axis.isEmpty() || outputData.y_axis.isEmpty()) {
        // You can show a loading indicator or an empty state message here
        return
    }
    //ChartEntryModelProducer()
    // 1. A model producer to hold and update the chart's data
    val modelProducer = remember { ChartEntryModelProducer() }

    // 2. This effect runs when your `outputData` changes, updating the chart
    LaunchedEffect(outputData) {
        val chartEntries = outputData.y_axis.mapIndexed { index, value ->
            // Create a chart entry: x is the position, y is the bar height
            entryOf(index.toFloat(), value.toFloat())
        }
        modelProducer.setEntries(chartEntries)
    }

    // 3. Formatter for the labels on the bottom (X) axis
    val bottomAxisValueFormatter =
        AxisValueFormatter<AxisPosition.Horizontal.Bottom> { value, _ ->
            // Use the x_axis list to get the label for each position
            outputData.x_axis.getOrNull(value.toInt()) ?: ""
        }

    // 4. Assemble the chart
    Chart(
        chart = columnChart(),
        chartModelProducer = modelProducer,
        startAxis = startAxis(), // The Y-axis on the left
        bottomAxis = bottomAxis( // The X-axis at the bottom
            valueFormatter = bottomAxisValueFormatter,
            guideline = null
        ),
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun preveiw(){
    val sampleOutput = Output(
        message = "Success",
        x_axis = listOf("Week 1", "Week 2", "Week 3", "Week 4"),
        y_axis = listOf(150.5, 210.0, 185.7, 250.2)
    )
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Text("Here's a summary of your water intake from August 20, 2025, to August 25, 2025:\n\n| Date       | Amount |\n|------------|--------|\n| 2025-08-20 | 3.10   |\n| 2025-08-21 | 2.90   |\n| 2025-08-22 | 3.20   |\n| 2025-08-23 | 2.60   |\n| 2025-08-24 | 3.50   |\n| 2025-08-25 | 2.80   |\n\nYour water intake over this period averaged around 3.00 units. The highest intake was 3.50 units on August 24, and the lowest was 2.60 units on August 23. \n\nYou consistently tracked your water intake over these six days, with no gaps in tracking. This means you have a streak of 6 consecutive days of tracking your water intake. The most recent streak is also 6 days, as you have been tracking your water intake every day from August 20 to August 25")
        //BarChartFromOutput(sampleOutput)

    }
}