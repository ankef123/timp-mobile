package com.example.puzzle

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.windowInsetsPadding
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

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

    var headerSize by remember { mutableStateOf(IntSize.Zero) }
    var controlsSize by remember { mutableStateOf(IntSize.Zero) }

    BoxWithConstraints(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .fillMaxSize()
    ) {
        val density = LocalDensity.current
        val screenPadding = 32.dp
        val contentWidthPx = with(density) {
            (maxWidth - screenPadding).coerceAtLeast(0.dp).roundToPx()
        }
        val sizesMatchCurrentWidth =
            headerSize.width == contentWidthPx &&
                controlsSize.width == contentWidthPx &&
                headerSize.height > 0 &&
                controlsSize.height > 0
        val measuredFixedHeight = if (sizesMatchCurrentWidth) {
            with(density) { (headerSize.height + controlsSize.height).toDp() }
        } else {
            0.dp
        }
        val contentSpacing = 20.dp
        val measuredMaxFrameHeight =
            (maxHeight - measuredFixedHeight - contentSpacing - screenPadding)
                .coerceIn(64.dp, 240.dp)
        val maxFrameHeight = if (sizesMatchCurrentWidth) measuredMaxFrameHeight else 64.dp
        val boundedFrameHeight = frameHeight.dp.coerceIn(64.dp, maxFrameHeight)
        val sliderValue = boundedFrameHeight.value
        val sliderMax = maxFrameHeight.value

        LaunchedEffect(maxFrameHeight) {
            val maximumHeight = maxFrameHeight.value.roundToInt()
            if (frameHeight > maximumHeight) {
                frameHeight = maximumHeight.coerceAtLeast(64)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Лабораторная работа №4",
                modifier = Modifier
                    .fillMaxWidth()
                    .onSizeChanged { size ->
                        if (headerSize != size) headerSize = size
                    }
            )
            FrameContainer(
                labelCount = labelCount,
                frameHeight = boundedFrameHeight,
                leftPadding = leftPadding.dp,
                topPadding = topPadding.dp,
                rightPadding = rightPadding.dp,
                bottomPadding = bottomPadding.dp,
                hasShadow = hasShadow,
                cornerRadius = cornerRadius.dp
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .onSizeChanged { size ->
                        if (controlsSize != size) controlsSize = size
                    },
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
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

                Text(
                    "Frame height: ${boundedFrameHeight.value.roundToInt()} dp " +
                        "(max ${maxFrameHeight.value.roundToInt()} dp)"
                )
                Slider(
                    value = sliderValue,
                    onValueChange = { frameHeight = it.roundToInt().coerceAtLeast(64) },
                    valueRange = 64f..sliderMax,
                    enabled = sliderMax > 64f,
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
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(frameHeight),
        shape = shape,
        color = Color(0xFF1976D2),
        border = BorderStroke(1.dp, Color(0xFF0D47A1)),
        shadowElevation = if (hasShadow) 12.dp else 0.dp
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
