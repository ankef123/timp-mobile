package com.example.puzzle

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private val paddingSides = listOf("left", "top", "right", "bottom")
private val cornerOptions = listOf(0, 10, 20, 30)

@Composable
fun Lab4Screen() {
    var labelCount by rememberSaveable { mutableIntStateOf(0) }
    var selectedSide by rememberSaveable { mutableStateOf("left") }
    var leftPadding by rememberSaveable { mutableIntStateOf(0) }
    var topPadding by rememberSaveable { mutableIntStateOf(0) }
    var rightPadding by rememberSaveable { mutableIntStateOf(0) }
    var bottomPadding by rememberSaveable { mutableIntStateOf(0) }
    var frameHeight by rememberSaveable { mutableIntStateOf(64) }
    var hasShadow by rememberSaveable { mutableStateOf(false) }
    var cornerRadius by rememberSaveable { mutableIntStateOf(0) }
    var pickerExpanded by rememberSaveable { mutableStateOf(false) }

    val selectedPadding = when (selectedSide) {
        "left" -> leftPadding
        "top" -> topPadding
        "right" -> rightPadding
        else -> bottomPadding
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val density = LocalDensity.current
        val maxHeightDp = with(density) { constraints.maxHeight.toDp() }
        val reservedControlsHeight = 300.dp
        val maxFrameHeight = (maxHeightDp - reservedControlsHeight)
            .coerceIn(64.dp, 240.dp)
        val currentFrameHeight = frameHeight.dp.coerceAtMost(maxFrameHeight)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text("Лабораторная работа №4")
            FrameContainer(
                labelCount = labelCount,
                frameHeight = currentFrameHeight,
                leftPadding = leftPadding.dp,
                topPadding = topPadding.dp,
                rightPadding = rightPadding.dp,
                bottomPadding = bottomPadding.dp,
                hasShadow = hasShadow,
                cornerRadius = cornerRadius.dp
            )
            Button(
                onClick = { if (labelCount < 20) labelCount += 1 },
                modifier = Modifier.fillMaxWidth(),
                enabled = labelCount < 20
            ) {
                Text("Добавить Label")
            }

            Text("Padding: $selectedPadding dp, $selectedSide")
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                BoxWithConstraints {
                    OutlinedButton(onClick = { pickerExpanded = true }) {
                        Text(selectedSide)
                    }
                    DropdownMenu(
                        expanded = pickerExpanded,
                        onDismissRequest = { pickerExpanded = false }
                    ) {
                        paddingSides.forEach { side ->
                            DropdownMenuItem(
                                text = { Text(side) },
                                onClick = {
                                    selectedSide = side
                                    pickerExpanded = false
                                }
                            )
                        }
                    }
                }
                OutlinedButton(
                    onClick = {
                        changeSelectedPadding(
                            selectedSide = selectedSide,
                            delta = -4,
                            left = leftPadding,
                            top = topPadding,
                            right = rightPadding,
                            bottom = bottomPadding,
                            setLeft = { leftPadding = it },
                            setTop = { topPadding = it },
                            setRight = { rightPadding = it },
                            setBottom = { bottomPadding = it }
                        )
                    }
                ) {
                    Text("−")
                }
                OutlinedButton(
                    onClick = {
                        changeSelectedPadding(
                            selectedSide = selectedSide,
                            delta = 4,
                            left = leftPadding,
                            top = topPadding,
                            right = rightPadding,
                            bottom = bottomPadding,
                            setLeft = { leftPadding = it },
                            setTop = { topPadding = it },
                            setRight = { rightPadding = it },
                            setBottom = { bottomPadding = it }
                        )
                    }
                ) {
                    Text("+")
                }
            }

            Text("Frame height: ${currentFrameHeight.value.toInt()} dp")
            Slider(
                value = currentFrameHeight.value,
                onValueChange = { frameHeight = it.toInt().coerceAtLeast(64) },
                valueRange = 64f..maxFrameHeight.value,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("HasShadow")
                Switch(checked = hasShadow, onCheckedChange = { hasShadow = it })
            }

            Text("CornerRadius")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                cornerOptions.forEach { radius ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = cornerRadius == radius,
                            onClick = { cornerRadius = radius }
                        )
                        Text(radius.toString())
                    }
                }
            }
        }
    }
}

private fun changeSelectedPadding(
    selectedSide: String,
    delta: Int,
    left: Int,
    top: Int,
    right: Int,
    bottom: Int,
    setLeft: (Int) -> Unit,
    setTop: (Int) -> Unit,
    setRight: (Int) -> Unit,
    setBottom: (Int) -> Unit
) {
    val (current, setter) = when (selectedSide) {
        "left" -> left to setLeft
        "top" -> top to setTop
        "right" -> right to setRight
        else -> bottom to setBottom
    }
    setter((current + delta).coerceIn(0, 64))
}

@Composable
private fun FrameContainer(
    labelCount: Int,
    frameHeight: Dp,
    leftPadding: Dp,
    topPadding: Dp,
    rightPadding: Dp,
    bottomPadding: Dp,
    hasShadow: Boolean,
    cornerRadius: Dp
) {
    val shape = RoundedCornerShape(cornerRadius)
    val shadowModifier = if (hasShadow) {
        Modifier.shadow(elevation = 8.dp, shape = shape)
    } else {
        Modifier
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(frameHeight)
            .clip(shape)
            .then(shadowModifier),
        shape = shape,
        color = Color(0xFF1976D2),
        border = BorderStroke(1.dp, Color(0xFF0D47A1))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    start = leftPadding,
                    top = topPadding,
                    end = rightPadding,
                    bottom = bottomPadding
                ),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            repeat(labelCount) { index ->
                Text(
                    text = "Label ${index + 1}",
                    color = Color.White
                )
            }
        }
    }
}
