package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

private val InvExLightColorScheme = lightColorScheme(
  primary = InvExBgDark,
  onPrimary = InvExTextInverse,
  primaryContainer = InvExBgCardSecondary,
  onPrimaryContainer = InvExTextPrimary,
  secondary = InvExTextSecondary,
  onSecondary = InvExTextInverse,
  background = InvExBgScreen,
  onBackground = InvExTextPrimary,
  surface = InvExBgCardPrimary,
  onSurface = InvExTextPrimary,
  surfaceVariant = InvExBgCardSecondary,
  onSurfaceVariant = InvExTextSecondary,
  outline = InvExBorderSubtle,
  outlineVariant = InvExBorderSubtle,
  error = InvExDangerRed,
  onError = InvExTextInverse
)

private val InvExDarkColorScheme = darkColorScheme(
  primary = InvExBgCardPrimary,
  onPrimary = InvExBgDark,
  primaryContainer = InvExBgDark,
  onPrimaryContainer = InvExTextInverse,
  secondary = InvExBorderSubtle,
  onSecondary = InvExTextPrimary,
  background = InvExBgDark,
  onBackground = InvExTextInverse,
  surface = InvExBgDark,
  onSurface = InvExTextInverse,
  surfaceVariant = InvExBgCardSecondary,
  onSurfaceVariant = InvExTextSecondary,
  outline = InvExTextSecondary,
  outlineVariant = InvExTextSecondary,
  error = InvExDangerRed,
  onError = InvExTextInverse
)

@Composable
fun InvExTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit
) {
  val colorScheme = if (darkTheme) InvExDarkColorScheme else InvExLightColorScheme
  val customColors = InvExColorScheme()
  val customTypography = InvExTypography()
  val customDimens = InvExDimens()

  CompositionLocalProvider(
    LocalInvExColors provides customColors,
    LocalInvExTypography provides customTypography,
    LocalInvExDimens provides customDimens
  ) {
    MaterialTheme(
      colorScheme = colorScheme,
      typography = Typography,
      shapes = InvExShapes,
      content = content
    )
  }
}

// Backwards compatibility alias for template references
@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit
) {
  InvExTheme(darkTheme = darkTheme, content = content)
}

// Semantic accessors on MaterialTheme
val MaterialTheme.invexColors: InvExColorScheme
  @Composable
  @ReadOnlyComposable
  get() = LocalInvExColors.current

val MaterialTheme.invexTypography: InvExTypography
  @Composable
  @ReadOnlyComposable
  get() = LocalInvExTypography.current

val MaterialTheme.invexDimens: InvExDimens
  @Composable
  @ReadOnlyComposable
  get() = LocalInvExDimens.current
