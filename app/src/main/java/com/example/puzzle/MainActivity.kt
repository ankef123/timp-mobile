package com.example.puzzle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.puzzle.ui.theme.PuzzleTheme

class MainActivity : ComponentActivity() {
    private var showSecondScreen by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PuzzleTheme {
                if (showSecondScreen) {
                    SecondScreen()
                } else {
                    FirstScreen()
                }
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        showSecondScreen = true
    }
}

@Preview(showBackground = true)
@Composable
private fun FirstScreenPreview() {
    PuzzleTheme {
        FirstScreen()
    }
}