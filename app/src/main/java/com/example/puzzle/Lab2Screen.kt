package com.example.puzzle

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val lab2Colors = listOf(Color.Red, Color.Green, Color.Yellow)
private val initialFontSizes = listOf(20f, 22f, 24f)
private val maximumFontSizes = listOf(28f, 30f, 32f)

@Composable
fun Lab2Screen() {
    var firstLabelAlignment by rememberSaveable { mutableStateOf(0) }
    var colorOffset by rememberSaveable { mutableStateOf(0) }
    var fontSizes by rememberSaveable { mutableStateOf(initialFontSizes) }
    var isHorizontal by rememberSaveable { mutableStateOf(false) }
    var spacing by rememberSaveable { mutableStateOf(8) }
    val canIncreaseFont = fontSizes.zip(maximumFontSizes).any { (size, maximum) ->
        size < maximum
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Лабораторная работа №2",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
        Text(
            text = "Управление текстом, кнопками и компоновкой",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        LabelGroup(
            isHorizontal = isHorizontal,
            spacing = spacing,
            firstLabelAlignment = firstLabelAlignment,
            colorOffset = colorOffset,
            fontSizes = fontSizes
        )

        Button(
            onClick = { firstLabelAlignment = (firstLabelAlignment + 1) % 3 },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Change Horizontal Option")
        }
        Button(
            onClick = { colorOffset = (colorOffset + 1) % lab2Colors.size },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Change Text Color")
        }
        Button(
            onClick = {
                fontSizes = fontSizes.mapIndexed { index, size ->
                    minOf(size + 2f, maximumFontSizes[index])
                }
            },
            enabled = canIncreaseFont,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Change Font Size")
        }
        Button(
            onClick = { isHorizontal = !isHorizontal },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Change Layout Orientation")
        }
        Button(
            onClick = { spacing = (spacing + 8).coerceAtMost(40) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Change Layout Spacing")
        }
    }
}

@Composable
private fun LabelGroup(
    isHorizontal: Boolean,
    spacing: Int,
    firstLabelAlignment: Int,
    colorOffset: Int,
    fontSizes: List<Float>
) {
    val labels = listOf("Метка 1", "Метка 2", "Метка 3")
    val alignments = listOf(
        when (firstLabelAlignment) {
            1 -> Alignment.Center
            2 -> Alignment.CenterEnd
            else -> Alignment.CenterStart
        },
        Alignment.Center,
        Alignment.Center
    )

    if (isHorizontal) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(spacing.dp)
        ) {
            labels.forEachIndexed { index, label ->
                Box(
                    modifier = Modifier.width(110.dp),
                    contentAlignment = alignments[index]
                ) {
                    LabelText(label, index, colorOffset, fontSizes[index])
                }
            }
        }
    } else {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(spacing.dp)
        ) {
            labels.forEachIndexed { index, label ->
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = alignments[index]
                ) {
                    LabelText(label, index, colorOffset, fontSizes[index])
                }
            }
        }
    }
}

@Composable
private fun LabelText(label: String, index: Int, colorOffset: Int, fontSize: Float) {
    val color = lab2Colors[(index + colorOffset) % lab2Colors.size]
    Text(
        text = label,
        color = color,
        fontSize = fontSize.sp
    )
}
