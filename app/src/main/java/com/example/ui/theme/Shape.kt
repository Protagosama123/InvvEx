package com.example.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class InvExDimens(
  val radiusSm: Dp = 8.dp,
  val radiusMd: Dp = 16.dp,
  val radiusLg: Dp = 24.dp,
  val radiusFull: Dp = 999.dp,
  val cardPadding: Dp = 16.dp,
  val screenHorizontalPadding: Dp = 20.dp,
  val spaceSm: Dp = 8.dp,
  val spaceMd: Dp = 12.dp,
  val spaceLg: Dp = 20.dp,
  val spaceXl: Dp = 28.dp
)

val LocalInvExDimens = staticCompositionLocalOf { InvExDimens() }

val InvExShapes = Shapes(
  small = RoundedCornerShape(8.dp),
  medium = RoundedCornerShape(16.dp),
  large = RoundedCornerShape(24.dp),
  extraLarge = RoundedCornerShape(32.dp)
)
