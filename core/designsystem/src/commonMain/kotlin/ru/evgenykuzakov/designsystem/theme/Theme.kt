package ru.evgenykuzakov.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = Blue,
    onPrimary = White,
    background = StrongGray,
    surface = DarkGray,
    surfaceContainer = Gray,
    surfaceContainerHigh = LightGray,
    onSurface = White,
    onSurfaceVariant = WeakWhite
)

private val DarkColors = darkColorScheme(
    primary = Blue,
    onPrimary = White,
    background = StrongGray,
    surface = DarkGray,
    surfaceContainer = Gray,
    surfaceContainerHigh = LightGray,
    onSurface = White,
    onSurfaceVariant = WeakWhite
)

@Composable
fun RiceTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (useDarkTheme) DarkColors else LightColors,
        typography = Typography,
        content = content
    )
}