package com.example.puzzle

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

// @Composable помечает функцию, которая описывает интерфейс Compose.
@Composable
fun FirstScreen() {
    // rememberSaveable сохраняет значение при пересоздании Activity.
    // mutableStateOf сообщает Compose, что изменение текста требует обновления UI.
    var text by rememberSaveable { mutableStateOf("") }

    // Column располагает элементы вертикально и поддерживает рекомпозицию.
    Column(
        // Modifier задаёт размеры и оформление без изменения самих компонентов.
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // Text — декларативный аналог TextView.
        Text(
            text = "First Android App",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "UI: Jetpack Compose\n\nEnter text and reverse it",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        // OutlinedTextField отображает управляемое поле ввода.
        OutlinedTextField(
            value = text,
            // onValueChange получает новый ввод пользователя и обновляет state.
            onValueChange = { text = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Text") },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Button вызывает onClick и автоматически обновляется после изменения state.
        Button(
            // onClick меняет состояние, после чего Compose выполняет рекомпозицию.
            onClick = { text = text.reversed() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Reverse Text")
        }
    }
}
