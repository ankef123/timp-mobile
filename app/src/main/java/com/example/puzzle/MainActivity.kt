package com.example.puzzle

import android.os.Bundle
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.puzzle.ui.theme.PuzzleTheme

class MainActivity : ComponentActivity() {
    private enum class Screen {
        MENU,
        COMPOSE,
        LAB2,
        LAB3,
        SECOND
    }

    private var screen by mutableStateOf(Screen.MENU)
    private var wasBackgrounded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        screen = savedInstanceState
            ?.getString(KEY_SCREEN)
            ?.let { value -> Screen.entries.firstOrNull { it.name == value } }
            ?: Screen.MENU
        enableEdgeToEdge()
        setContent {
            PuzzleTheme {
                BackHandler(enabled = screen != Screen.MENU) {
                    screen = Screen.MENU
                }
                when (screen) {
                    Screen.MENU -> DemoMenuScreen(
                        onComposeClick = { screen = Screen.COMPOSE },
                        onLab2Click = { screen = Screen.LAB2 },
                        onLab3Click = { screen = Screen.LAB3 },
                        onClassicClick = {
                            startActivity(Intent(this, ClassicActivity::class.java))
                        }
                    )
                    Screen.COMPOSE -> FirstScreen()
                    Screen.LAB2 -> Lab2Screen()
                    Screen.LAB3 -> Lab3Screen()
                    Screen.SECOND -> SecondScreen()
                }
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        if (screen == Screen.COMPOSE) {
            screen = Screen.SECOND
        }
    }

    override fun onStop() {
        super.onStop()
        if (!isChangingConfigurations) {
            wasBackgrounded = true
        }
    }

    override fun onStart() {
        super.onStart()
        if (wasBackgrounded && !isChangingConfigurations) {
            if (screen == Screen.COMPOSE) {
                screen = Screen.SECOND
            }
            wasBackgrounded = false
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(KEY_SCREEN, screen.name)
        super.onSaveInstanceState(outState)
    }

    private companion object {
        const val KEY_SCREEN = "screen"
    }
}

@Preview(showBackground = true)
@Composable
private fun FirstScreenPreview() {
    PuzzleTheme {
        FirstScreen()
    }
}