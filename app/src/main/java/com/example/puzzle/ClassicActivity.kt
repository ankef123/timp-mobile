package com.example.puzzle

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.puzzle.classic.ClassicFirstScreen

class ClassicActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ClassicFirstScreen(this))
    }
}
