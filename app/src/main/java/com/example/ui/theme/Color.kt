package com.example.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Core Design Tokens
val InvExBgScreen = Color(0xFFF2F2F2)
val InvExBgCardPrimary = Color(0xFFFFFFFF)
val InvExBgCardSecondary = Color(0xFFE8E8E8)
val InvExBgDark = Color(0xFF262626)

val InvExTextPrimary = Color(0xFF000000)
val InvExTextSecondary = Color(0xFF555555)
val InvExTextMuted = Color(0xFF888888)
val InvExTextInverse = Color(0xFFFFFFFF)

val InvExBorderSubtle = Color(0xFFD9D9D9)
val InvExBorderDark = Color(0xFF000000)

val InvExAccentLime = Color(0xFFD4E795)
val InvExAccentLimeLight = Color(0xFFE2F1A7)
val InvExDangerRed = Color(0xFFD93025)
val InvExWarningAmber = Color(0xFFF59E0B)

val InvExUpcomingGradient = Brush.linearGradient(
  listOf(Color(0xFFA8E063), Color(0xFF56AB2F))
)

@Immutable
data class InvExColorScheme(
  val bgScreen: Color = InvExBgScreen,
  val bgCardPrimary: Color = InvExBgCardPrimary,
  val bgCardSecondary: Color = InvExBgCardSecondary,
  val bgDark: Color = InvExBgDark,
  val textPrimary: Color = InvExTextPrimary,
  val textSecondary: Color = InvExTextSecondary,
  val textMuted: Color = InvExTextMuted,
  val textInverse: Color = InvExTextInverse,
  val borderSubtle: Color = InvExBorderSubtle,
  val borderDark: Color = InvExBorderDark,
  val accentLime: Color = InvExAccentLime,
  val accentLimeLight: Color = InvExAccentLimeLight,
  val dangerRed: Color = InvExDangerRed,
  val warningAmber: Color = InvExWarningAmber
)

val LocalInvExColors = staticCompositionLocalOf { InvExColorScheme() }
