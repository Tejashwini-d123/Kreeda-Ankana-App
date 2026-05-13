package com.example.kreedaankana.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(

    primary = PrimaryGreen,
    secondary = AccentOrange,
    background = BackgroundColor,
    surface = White,

    onPrimary = White,
    onSecondary = White,
    onBackground = TextBlack,
    onSurface = TextBlack
)

private val DarkColors = darkColorScheme(

    primary = DarkGreen,
    secondary = AccentOrange,

    onPrimary = White
)

@Composable
fun KreedaAnkanaTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography,
        content = content
    )
}