# Демонстрация Jetpack Compose и Android Views

## Цель демонстрации

Приложение показывает два способа создать одинаковый экран на Kotlin:
декларативный Jetpack Compose и классические Android Views. В обоих вариантах
есть поле ввода и кнопка `Reverse Text`.

## Сравнение компонентов

| Jetpack Compose | Android Views |
|---|---|
| `Column` | `LinearLayout` |
| `Text` | `TextView` |
| `OutlinedTextField` | `EditText` |
| `Button` | `android.widget.Button` |
| `onClick` | `setOnClickListener` |
| `mutableStateOf` | Состояние View / обычные переменные |
| `Modifier.padding` | `setPadding` |
| Изменение State | `setText` |

## Архитектура

`MainActivity` показывает меню `TIMP Mobile — UI Comparison`. Кнопка
`Jetpack Compose` открывает существующий [FirstScreen.kt](../app/src/main/java/com/example/puzzle/FirstScreen.kt),
а кнопка `Classic Android Views` запускает `ClassicActivity`, в которой
программно создаётся [ClassicFirstScreen.kt](../app/src/main/java/com/example/puzzle/classic/ClassicFirstScreen.kt).
Кнопка Back возвращает в меню.

Классическая реализация не использует XML и не использует `AndroidView` внутри
Compose: все `LinearLayout`, `TextView`, `EditText` и `Button` создаются
непосредственно кодом Kotlin.

## `@Composable` и обычная функция

`@Composable` — аннотация, сообщающая компилятору Compose, что функция описывает
часть интерфейса и может участвовать в рекомпозиции. Такую функцию нельзя
вызывать из обычного Kotlin-контекста без Compose-контекста, потому что она
использует специальный механизм композиции.

Рекомпозиция — повторный вызов нужных composable-функций после изменения
наблюдаемого состояния. В Android Views свойства конкретного объекта изменяются
непосредственно: например, `input.setText(...)`; автоматической рекомпозиции
нет. Поэтому классической функции создания View аннотация `@Composable` не нужна.

## Построчное сравнение

Compose:

```kotlin
var text by rememberSaveable { mutableStateOf("") }

Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
    OutlinedTextField(
        value = text,
        onValueChange = { text = it }
    )
    Button(onClick = { text = text.reversed() }) {
        Text("Reverse Text")
    }
}
```

Android Views:

```kotlin
val layout = LinearLayout(context).apply {
    orientation = LinearLayout.VERTICAL
    setPadding(dp(context, 24), dp(context, 24), dp(context, 24), dp(context, 24))
}
val input = EditText(context)
val button = Button(context).apply {
    text = "Reverse Text"
    setOnClickListener {
        val oldText = input.text.toString()
        input.setText(oldText.reversed())
    }
}
layout.addView(input)
layout.addView(button)
setContentView(layout)
```

В Compose состояние хранится в `mutableStateOf`, а поле получает новое значение
через `onValueChange`. В Views обработчик получает текст из `EditText` и
напрямую устанавливает его обратно через `setText`.

## Сценарий защиты

1. Открыть [FirstScreen.kt](../app/src/main/java/com/example/puzzle/FirstScreen.kt)
   и показать `@Composable`, `rememberSaveable`, `mutableStateOf`, `Column`,
   `OutlinedTextField` и `Button`.
2. Запустить приложение на Android Emulator.
3. В меню открыть `Jetpack Compose` и проверить `Reverse Text`.
4. Вернуться кнопкой Back в меню.
5. Открыть `Classic Android Views`, показать `LinearLayout`, `EditText`,
   `Button` и `setOnClickListener`.
6. Повторить проверку разворота текста, включая кириллицу и пустую строку.
7. Вернуться в меню.
8. Для проверки исходной лабораторной открыть Compose-версию, нажать Home и
   вернуться в приложение. Должен появиться `SecondScreen`.

Сценарий жизненного цикла из лабораторной сохранён: `onRestart()` и
контрольная пара `onStop()`/`onStart()` переводят только Compose-экран в
`SecondScreen`. Проверка `isChangingConfigurations` не считает обычный поворот
экрана возвратом из фона. Если приложение возвращается из фона на меню или
после классической Activity, меню не заменяется вторым экраном. Состояние
текущего режима также сохраняется при пересоздании Activity.

## Запуск

```bash
cd ~/AndroidStudioProjects/puzzle
emulator -avd Pixel_10a
adb devices
./gradlew installDebug
adb shell am start -n com.example.puzzle/.MainActivity
```

Сборка проверяется командой:

```bash
./gradlew clean assembleDebug
```
