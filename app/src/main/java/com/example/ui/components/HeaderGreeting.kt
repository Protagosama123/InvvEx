package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ui.theme.InvExTheme
import com.example.ui.theme.invexColors
import com.example.ui.theme.invexTypography

/**
 * Header greeting with the signature inline pen-and-lines icon:
 * "Stay ready, your ≡✎ exam"
 * "awaits"
 */
@Composable
fun HeaderGreeting(
  modifier: Modifier = Modifier
) {
  val textStyle = MaterialTheme.invexTypography.headingXl
  val textColor = MaterialTheme.invexColors.textPrimary

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 8.dp)
      .testTag("header_user_greeting")
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "Stay ready, your ",
        style = textStyle,
        color = textColor
      )

      // Inline lines + pen icon
      ExamPenIcon(
        modifier = Modifier
          .padding(horizontal = 2.dp)
          .size(26.dp),
        color = textColor
      )

      Text(
        text = " exam",
        style = textStyle,
        color = textColor
      )
    }

    Text(
      text = "awaits",
      style = textStyle,
      color = textColor
    )
  }
}

/**
 * Custom vector icon representing notes lines + pen ("≡✎")
 */
@Composable
fun ExamPenIcon(
  modifier: Modifier = Modifier,
  color: Color = Color.Black
) {
  Canvas(modifier = modifier) {
    val w = size.width
    val h = size.height

    // 2 horizontal note lines on the left
    val strokeW = 2.2.dp.toPx()
    drawLine(
      color = color,
      start = Offset(w * 0.05f, h * 0.40f),
      end = Offset(w * 0.45f, h * 0.40f),
      strokeWidth = strokeW,
      cap = StrokeCap.Round
    )
    drawLine(
      color = color,
      start = Offset(w * 0.05f, h * 0.65f),
      end = Offset(w * 0.45f, h * 0.65f),
      strokeWidth = strokeW,
      cap = StrokeCap.Round
    )

    // Slanted pen pointing downwards to the left
    val penPath = Path().apply {
      // Pen tip
      moveTo(w * 0.55f, h * 0.85f)
      lineTo(w * 0.65f, h * 0.65f)
      lineTo(w * 0.95f, h * 0.25f)
      lineTo(w * 0.85f, h * 0.15f)
      lineTo(w * 0.55f, h * 0.55f)
      close()
    }
    drawPath(path = penPath, color = color)
  }
}

@Preview(showBackground = true, backgroundColor = 0xFFF2F2F2)
@Composable
fun HeaderGreetingPreview() {
  InvExTheme {
    HeaderGreeting()
  }
}
