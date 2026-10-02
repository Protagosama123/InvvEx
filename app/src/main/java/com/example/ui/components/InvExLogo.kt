package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.InvExTheme
import com.example.ui.theme.invexColors

/**
 * InvEx brand logo displaying the bold brand typography with a student mortarboard cap.
 */
@Composable
fun InvExLogo(
  modifier: Modifier = Modifier
) {
  val textColor = MaterialTheme.invexColors.textPrimary

  Box(
    modifier = modifier.testTag("invex_logo"),
    contentAlignment = Alignment.CenterStart
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Text(
        text = "Inv",
        fontSize = 30.sp,
        fontWeight = FontWeight.ExtraBold,
        fontFamily = FontFamily.SansSerif,
        color = textColor,
        letterSpacing = (-0.5).sp
      )
      Text(
        text = "Ex",
        fontSize = 30.sp,
        fontWeight = FontWeight.ExtraBold,
        fontFamily = FontFamily.SansSerif,
        color = textColor,
        letterSpacing = (-0.5).sp
      )
    }

    // Mortarboard graduation cap sitting on top of the 'nv'
    MortarboardCap(
      modifier = Modifier
        .size(24.dp)
        .offset(x = 18.dp, y = (-12).dp),
      color = textColor
    )
  }
}

@Composable
fun MortarboardCap(
  modifier: Modifier = Modifier,
  color: Color = Color.Black
) {
  Canvas(modifier = modifier) {
    val w = size.width
    val h = size.height

    // Rhombus top of cap
    val diamondPath = Path().apply {
      moveTo(w * 0.5f, h * 0.1f)
      lineTo(w * 0.95f, h * 0.35f)
      lineTo(w * 0.5f, h * 0.6f)
      lineTo(w * 0.05f, h * 0.35f)
      close()
    }
    drawPath(
      path = diamondPath,
      color = color,
      style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round, join = StrokeJoin.Round)
    )

    // Cap base skullcap
    val baseCapPath = Path().apply {
      moveTo(w * 0.28f, h * 0.5f)
      quadraticBezierTo(w * 0.5f, h * 0.78f, w * 0.72f, h * 0.5f)
    }
    drawPath(
      path = baseCapPath,
      color = color,
      style = Stroke(width = 2.5.dp.toPx(), cap = StrokeCap.Round)
    )

    // Tassel dangling from center to right
    val tasselPath = Path().apply {
      moveTo(w * 0.5f, h * 0.35f)
      lineTo(w * 0.88f, h * 0.42f)
      lineTo(w * 0.88f, h * 0.82f)
    }
    drawPath(
      path = tasselPath,
      color = color,
      style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
    )

    // Tassel knob / end
    drawCircle(
      color = color,
      radius = 2.5.dp.toPx(),
      center = Offset(w * 0.88f, h * 0.85f)
    )
  }
}

@Preview(showBackground = true)
@Composable
fun InvExLogoPreview() {
  InvExTheme {
    InvExLogo()
  }
}
