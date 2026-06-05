package com.signlink.app.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// ── Light Color Scheme ────────────────────────────────────────
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF008399), // SignLinkTeal400
    onPrimary = Color.White,
    primaryContainer = Color(0xFFADEAFF), // SignLinkTeal100
    onPrimaryContainer = Color(0xFF001F26), // Primary10
    
    secondary = Color(0xFF006173), // SignLinkTeal500
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFD6F6FF), // Primary95
    onSecondaryContainer = Color(0xFF00363F), // Primary20
    
    tertiary = Color(0xFF6D5677),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFF6D9FF),
    onTertiaryContainer = Color(0xFF251431),
    
    error = Error,
    onError = Color.White,
    
    background = Color(0xFFD6F6FF), // Light Sky Blue
    onBackground = Color(0xFF001F26),
    surface = Color.White,
    onSurface = Color(0xFF001F26),
    surfaceVariant = Color(0xFFADEAFF),
    onSurfaceVariant = Color(0xFF004F5C),
    outline = Color(0xFF00697A)
)

// ── Dark Color Scheme ─────────────────────────────────────────
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF00E5FF), // SignLinkCyan - Brighter for Dark Mode
    onPrimary = Color(0xFF001F26),
    primaryContainer = Color(0xFF004F5C), // Primary30
    onPrimaryContainer = Color(0xFFADEAFF),
    
    secondary = Color(0xFF55D6F3), // Primary80
    onSecondary = Color(0xFF00363F),
    secondaryContainer = Color(0xFF00363F),
    onSecondaryContainer = Color(0xFFADEAFF),
    
    tertiary = Color(0xFFD9BDE3),
    onTertiary = Color(0xFF251431),
    tertiaryContainer = Color(0xFF6D5677),
    onTertiaryContainer = Color(0xFFF6D9FF),
    
    error = Color(0xFFF2B8B5),
    onError = Color(0xFF601410),
    
    background = Color(0xFF001D26), // SignLinkTeal900
    onBackground = Color(0xFFADEAFF),
    surface = Color(0xFF001F26), // Primary10
    onSurface = Color(0xFFADEAFF),
    surfaceVariant = Color(0xFF00363F),
    onSurfaceVariant = Color(0xFFADEAFF),
    outline = Color(0xFF00697A)
)

// ── High Contrast Color Scheme ────────────────────────────────
private val HighContrastColorScheme = darkColorScheme(
    primary = HighContrastAccent,
    onPrimary = HighContrastBackground,
    primaryContainer = HighContrastSurface,
    onPrimaryContainer = HighContrastText,
    background = HighContrastBackground,
    onBackground = HighContrastText,
    surface = HighContrastSurface,
    onSurface = HighContrastText,
    outline = HighContrastAccent
)

@Composable
fun SignLinkTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    highContrast: Boolean = false,
    dynamicColor: Boolean = false, // Disabled to keep the branded blue theme as default
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        highContrast -> HighContrastColorScheme
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.surface.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = SignLinkTypography,
        content = content
    )
}
