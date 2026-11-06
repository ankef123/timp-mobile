# Отчёт по лабораторной работе №1

## Цель и стек

Изучены структура Android/Gradle-проекта, Jetpack Compose, ввод текста,
эмулятор и переход Activity из фонового состояния.

- Android Gradle Plugin: `9.4.0` (из `gradle/libs.versions.toml`)
- Kotlin plugin: `2.2.10`
- Gradle: `9.6.0`
- JDK: OpenJDK `17.0.20.1`
- AVD: `Pixel_10a`, Android `37.1`, `x86_64`

Версия Kotlin `2.3.21`, напечатанная `./gradlew --version`, относится к
встроенной Kotlin-версии Gradle; версия плагина проекта указана выше.

## Структура проекта

- `app/src/main/java/com/example/puzzle/MainActivity.kt` — точка входа
  Activity, установка Compose-контента и обработка `onRestart`.
- `app/src/main/AndroidManifest.xml` — описание приложения, launcher-Activity,
  имя приложения и тема.
- `app/build.gradle.kts` — настройки модуля `:app`: SDK, application ID,
  Compose и зависимости.
- `build.gradle.kts` — общие плагины корневого Gradle-проекта.
- `settings.gradle.kts` — имя Gradle-проекта, репозитории и подключённые модули.
- `gradlew` — Gradle Wrapper, запускающий зафиксированную версию Gradle без
  обязательной глобальной установки Gradle.

Gradle-проект — это контейнер сборки, который может содержать несколько
модулей. `:app` — Android-модуль, из которого собирается APK. Аналогия с
Xamarin/Visual Studio: корень Gradle-проекта близок к solution, а `:app` —
к project, но Android-модули и их зависимости описываются Gradle-файлами.

Опция «поместить решение и проект в одном каталоге» в Xamarin управляла
размещением solution-файла и project-файла. В Gradle корень и модули изначально
разделены (`settings.gradle.kts` и `app/`), поэтому структура лучше подходит
для multi-module-сборок; при этом один модуль вполне может находиться в одном
рабочем каталоге с корнем.

## Первый и второй экраны

`FirstScreen.kt` содержит управляемое через `rememberSaveable` поле ввода и
кнопку `Reverse Text`. Операция `String.reversed()` корректно работает с
пустой строкой и русским текстом; повторное нажатие возвращает исходную
последовательность символов.

`SecondScreen.kt` содержит отдельный Compose-интерфейс. Первый экран
показывается при `onCreate`, а `MainActivity.onRestart()` устанавливает
состояние второго экрана. `onRestart` вызывается после остановки Activity при
возврате из фона, но не при обычной рекомпозиции; пересоздание Activity при
повороте начинается с `onCreate`, поэтому не считается возвратом из фона.

Проверенный сценарий:

1. После свежего запуска отображается `First Android App`.
2. После Home и возврата через launcher отображается `Second Screen`.

Скриншоты: [первый экран](./assets/lab1_result_first.png) и [экран после
возврата из фона](./assets/lab1_result_second.png).

Отображаемое имя задаётся ресурсом `app_name` в
`app/src/main/res/values/strings.xml` и используется как
`android:label="@string/app_name"` в Manifest. Имя папки и `applicationId`
для этого менять не требуется.

## Эмулятор и устройства

Конфигурация `Pixel_10a`:

| Параметр | Назначение и фактическое значение |
|---|---|
| `hw.battery` | симуляция батареи; `yes`/`no`, сейчас `yes` |
| `hw.camera.back` | задняя камера; `virtualscene` |
| `hw.cpu.ncore` | число виртуальных ядер; сейчас `4` |
| `hw.gps` | GPS; `yes` |
| `hw.gpu.mode` | режим GPU; `host` (также встречаются `swiftshader`, `mesa`) |
| `hw.ramSize` | RAM AVD в MiB; сейчас `8192` |
| `hw.sdCard` | наличие виртуальной SD-карты; `yes` |
| `sdcard.size` | размер SD-карты; сейчас `512M` |
| `vm.heapSize` | размер heap Dalvik/ART в MiB; сейчас `228` |

Допустимые значения зависят от версии Emulator, устройства и системного
образа; старые AVD могут иметь другие ключи и значения, чем текущий Device
Manager. На Ubuntu аппаратная виртуализация выполняется через KVM
(модуль ядра Linux), а Intel HAXM был отдельным гипервизором для Intel и
устарел в современных конфигурациях. KVM обычно выбирается автоматически,
если доступна аппаратная виртуализация.

Выполнены команды:

```text
./gradlew assembleDebug       BUILD SUCCESSFUL
adb devices                   emulator-5554 device
./gradlew installDebug        Installed on 1 device
```

Физическое устройство не подключено. Для запуска на нём нужно включить
Developer options и USB debugging, подтвердить RSA-ключ на телефоне,
проверить устройство командой `adb devices`, затем выполнить
`./gradlew installDebug`. iOS и UWP в нативном Android-проекте не запускаются:
для них нужны отдельные платформенные проекты и соответствующие среды.
