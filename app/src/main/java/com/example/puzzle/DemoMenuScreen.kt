package com.example.puzzle

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun DemoMenuScreen(
    onComposeClick: () -> Unit,
    onLab2Click: () -> Unit,
    onLab3Click: () -> Unit,
    onLab4Click: () -> Unit,
    onClassicClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "TIMP Mobile — UI Comparison",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onComposeClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Jetpack Compose")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = onLab2Click,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Laboratory 2")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = onLab3Click,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Laboratory 3")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = onLab4Click,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Laboratory 4")
        }
        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = onClassicClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Classic Android Views")
        }
    }
}
