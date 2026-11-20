package com.example.puzzle

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import kotlinx.coroutines.delay

@Composable
fun Lab3Screen() {
    var activeIndex by rememberSaveable { mutableStateOf(0) }
    var switchCount by rememberSaveable { mutableStateOf(3) }
    var autoMode by rememberSaveable { mutableStateOf(false) }
    var isStarted by rememberSaveable { mutableStateOf(true) }
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> isStarted = true
                Lifecycle.Event.ON_STOP -> isStarted = false
                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val density = LocalDensity.current
        val maxHeightDp = with(density) { constraints.maxHeight.toDp() }
        val rowCapacity =
            ((maxHeightDp.value - 220f) / 56f).toInt().coerceAtLeast(3)
        val switchCapacity = (rowCapacity / 3 * 3).coerceAtLeast(3)

        LaunchedEffect(autoMode, isStarted, switchCount) {
            if (autoMode && isStarted) {
                while (true) {
                    delay(1_000)
                    val nextIndex = (activeIndex + 1) % switchCount
                    if (nextIndex == 0 && switchCount < switchCapacity) {
                        switchCount = (switchCount + 3).coerceAtMost(switchCapacity)
                    }
                    activeIndex = nextIndex
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Лабораторная работа №3")
            Text("Переключатели и автоматическое переключение")
            Spacer(modifier = Modifier.height(8.dp))
            repeat(switchCount) { index ->
                StepSwitch(
                    label = (index + 1).toString(),
                    checked = activeIndex == index,
                    enabled = !autoMode && activeIndex == index,
                    onCheckedChange = {
                        val nextIndex = (activeIndex + 1) % switchCount
                        if (nextIndex == 0 && switchCount < switchCapacity) {
                            switchCount = (switchCount + 3).coerceAtMost(switchCapacity)
                        }
                        activeIndex = nextIndex
                    }
                )
            }
            HorizontalDivider()
            StepSwitch(
                label = "Автоматический режим",
                checked = autoMode,
                enabled = true,
                onCheckedChange = { autoMode = it }
            )
            Text(
                text = if (autoMode) {
                    "Автоматический переход: примерно каждые 1 сек."
                } else {
                    "Ручной режим: активен шаг ${activeIndex + 1}"
                }
            )
        }
    }
}

@Composable
private fun StepSwitch(
    label: String,
    checked: Boolean,
    enabled: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label)
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = enabled
        )
    }
}
