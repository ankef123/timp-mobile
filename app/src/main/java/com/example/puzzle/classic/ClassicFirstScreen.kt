package com.example.puzzle.classic

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView

// Создаёт тот же экран на Android Views, чтобы его можно было сравнить с Compose.
fun ClassicFirstScreen(context: Context): View {
    // LinearLayout — императивный вертикальный контейнер для дочерних View.
    val layout = LinearLayout(context).apply {
        orientation = LinearLayout.VERTICAL
        gravity = Gravity.CENTER
        setPadding(dp(context, 24), dp(context, 24), dp(context, 24), dp(context, 24))
    }

    // TextView отображает заголовок как обычный Android View.
    val title = TextView(context).apply {
        text = "First Android App"
        textSize = 20f
        gravity = Gravity.CENTER
    }
    // TextView отображает подпись, указывающую используемую технологию.
    val technology = TextView(context).apply {
        text = "UI: Android Views"
        textSize = 14f
        setTextColor(Color.DKGRAY)
        gravity = Gravity.CENTER
    }
    val description = TextView(context).apply {
        text = "Enter text and reverse it"
        gravity = Gravity.CENTER
    }
    // EditText — редактируемое поле ввода без Compose-состояния.
    val input = EditText(context).apply {
        hint = "Text"
        setSingleLine(true)
    }
    // Button — стандартная кнопка Android Views.
    val reverseButton = Button(context).apply {
        text = "Reverse Text"
        // setOnClickListener выполняет код непосредственно при нажатии.
        setOnClickListener {
            // getText()/text получает текущую строку из EditText.
            val oldText = input.text.toString()
            // setText обновляет текст самого View.
            input.setText(oldText.reversed())
        }
    }

    // addView добавляет созданные элементы в LinearLayout в нужном порядке.
    layout.addView(title, params(MATCH, WRAP, 8))
    layout.addView(technology, params(MATCH, WRAP, 8))
    layout.addView(description, params(MATCH, WRAP, 24))
    layout.addView(input, params(MATCH, WRAP, 16))
    layout.addView(reverseButton, params(MATCH, WRAP, 0))
    // Activity передаёт этот корневой View в setContentView.
    return layout
}

private const val MATCH = -1
private const val WRAP = -2

private fun params(width: Int, height: Int, topMargin: Int): LinearLayout.LayoutParams =
    LinearLayout.LayoutParams(width, height).apply {
        if (topMargin > 0) setMargins(0, topMargin, 0, 0)
    }

private fun dp(context: Context, value: Int): Int =
    (value * context.resources.displayMetrics.density).toInt()
