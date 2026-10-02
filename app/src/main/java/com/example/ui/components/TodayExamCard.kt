package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.InvExTheme
import com.example.ui.theme.invexColors
import com.example.ui.theme.invexDimens
import com.example.ui.theme.invexTypography

/**
 * Today Exam Progress Card displaying current course status,
 * countdown, milestone timeline, and "View Your Exams" CTA.
 */
@Composable
fun TodayExamCard(
  courseName: String = "Object oriented programming in java",
  courseId: String = "1234567890",
  startingIn: String = "0h 15m",
  completedExams: Int = 1,
  totalExams: Int = 5,
  onViewExamsClick: () -> Unit = {},
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 6.dp)
      .testTag("today_exam_section")
  ) {
    // Section Header
    Text(
      text = "Today Exam",
      style = MaterialTheme.invexTypography.headingLg,
      color = MaterialTheme.invexColors.textPrimary,
      modifier = Modifier.padding(bottom = 12.dp)
    )

    // Main Card
    Card(
      modifier = Modifier.fillMaxWidth(),
      shape = RoundedCornerShape(MaterialTheme.invexDimens.radiusLg),
      colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.invexColors.bgCardPrimary
      ),
      elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(18.dp)
      ) {
        // Course Title
        Text(
          text = courseName,
          style = MaterialTheme.invexTypography.headingMd,
          color = MaterialTheme.invexColors.textPrimary
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Course ID
        Text(
          text = "Course Id : $courseId",
          style = MaterialTheme.invexTypography.bodyDefault,
          color = MaterialTheme.invexColors.textSecondary
        )

        Spacer(modifier = Modifier.height(2.dp))

        // Starting Countdown
        Text(
          text = "Starting in : $startingIn",
          style = MaterialTheme.invexTypography.bodyDefault,
          color = MaterialTheme.invexColors.textSecondary
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Visual Milestone Timeline with "~Your Next Exam" Callout
        ExamProgressTimeline(
          completedCount = completedExams,
          totalCount = totalExams,
          modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
        )

        // Progress Text
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(top = 2.dp, bottom = 16.dp)
        ) {
          Box(
            modifier = Modifier
              .size(8.dp)
              .clip(CircleShape)
              .background(MaterialTheme.invexColors.bgDark)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "$completedExams / $totalExams exam completed",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.invexColors.textSecondary
          )
        }

        // Full-width Action Button: "View Your Exams"
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(MaterialTheme.invexDimens.radiusMd))
            .background(MaterialTheme.invexColors.bgCardSecondary)
            .clickable(
              role = Role.Button,
              onClick = onViewExamsClick
            )
            .testTag("btn_view_your_exams"),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "View Your Exams",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.invexColors.textPrimary
          )
        }
      }
    }
  }
}

/**
 * Canvas drawing the timeline with completed segment, milestone dots,
 * and the handwritten callout curve to "~Your Next Exam".
 */
@Composable
fun ExamProgressTimeline(
  completedCount: Int,
  totalCount: Int,
  modifier: Modifier = Modifier
) {
  val lineColor = MaterialTheme.invexColors.borderSubtle
  val completedColor = MaterialTheme.invexColors.bgDark
  val textColor = MaterialTheme.invexColors.textPrimary

  Box(modifier = modifier) {
    Canvas(modifier = Modifier.fillMaxWidth().height(54.dp)) {
      val w = size.width
      val trackY = size.height * 0.75f
      val step = w / (totalCount)

      // Base Track Line
      drawLine(
        color = lineColor,
        start = Offset(0f, trackY),
        end = Offset(w, trackY),
        strokeWidth = 2.dp.toPx(),
        cap = StrokeCap.Round
      )

      // Completed Track segment
      val completedX = step * 0.6f
      drawRoundRect(
        color = completedColor,
        topLeft = Offset(step * 0.2f, trackY - 3.dp.toPx()),
        size = androidx.compose.ui.geometry.Size(completedX, 6.dp.toPx()),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(4.dp.toPx(), 4.dp.toPx())
      )

      // First dot (completed start)
      drawCircle(
        color = completedColor.copy(alpha = 0.3f),
        radius = 5.dp.toPx(),
        center = Offset(step * 0.2f, trackY)
      )

      // Dot 1 end
      drawCircle(
        color = completedColor,
        radius = 4.dp.toPx(),
        center = Offset(step * 0.8f, trackY)
      )

      // Next exam target dot (milestone 2)
      val nextExamX = step * 1.8f
      drawCircle(
        color = completedColor,
        radius = 4.5.dp.toPx(),
        center = Offset(nextExamX, trackY)
      )

      // Remaining milestone dots (3, 4, 5)
      for (i in 3..totalCount) {
        val dotX = step * (i - 0.2f)
        if (dotX <= w) {
          drawCircle(
            color = lineColor,
            radius = 3.5.dp.toPx(),
            center = Offset(dotX, trackY)
          )
        }
      }

      // Curved indicator line from "~Your Next Exam" down to next exam dot
      val arrowPath = Path().apply {
        moveTo(nextExamX + 40.dp.toPx(), trackY - 26.dp.toPx())
        cubicTo(
          nextExamX + 10.dp.toPx(), trackY - 28.dp.toPx(),
          nextExamX - 2.dp.toPx(), trackY - 14.dp.toPx(),
          nextExamX, trackY - 3.dp.toPx()
        )
      }
      drawPath(
        path = arrowPath,
        color = completedColor,
        style = Stroke(width = 1.5.dp.toPx(), cap = StrokeCap.Round)
      )
    }

    // Callout text overlay "~Your Next Exam"
    Row(
      modifier = Modifier
        .align(Alignment.TopCenter)
        .padding(start = 20.dp, top = 2.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "~Your Next Exam",
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = FontFamily.SansSerif,
        color = textColor
      )
    }
  }
}

@Preview(showBackground = true, backgroundColor = 0xFFF2F2F2)
@Composable
fun TodayExamCardPreview() {
  InvExTheme {
    TodayExamCard()
  }
}
