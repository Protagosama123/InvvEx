package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.DataArray
import androidx.compose.material.icons.outlined.TableChart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.InvExTheme
import com.example.ui.theme.invexColors
import com.example.ui.theme.invexDimens
import com.example.ui.theme.invexTypography
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * Interactive Notification Banner Card (SwipeToDismissBanner):
 * - Top Row: Course badges (OOPS {}, ADS [田])
 * - Bottom Dark Alert Bar: "You have 2 exams today" with draggable article handle and close button.
 * - Supports direct tap dismissal and slide-to-dismiss gesture.
 */
@Composable
fun SwipeToDismissBanner(
  modifier: Modifier = Modifier,
  visible: Boolean = true,
  onDismiss: () -> Unit = {},
  onBadgeClick: (String) -> Unit = {}
) {
  AnimatedVisibility(
    visible = visible,
    enter = fadeIn(animationSpec = tween(300)),
    exit = fadeOut(animationSpec = tween(250)) + shrinkVertically(animationSpec = tween(300)),
    modifier = modifier.testTag("banner_container")
  ) {
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 6.dp),
      shape = RoundedCornerShape(MaterialTheme.invexDimens.radiusLg),
      colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.invexColors.bgCardPrimary
      ),
      elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        // Top Row: Course Badges
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
        ) {
          // OOPS Course Badge
          CourseBadge(
            tag = "OOPS",
            iconType = CourseIconType.BRACKETS,
            onClick = { onBadgeClick("OOPS") },
            modifier = Modifier.testTag("badge_oops")
          )

          Spacer(modifier = Modifier.width(16.dp))

          // ADS Course Badge
          CourseBadge(
            tag = "ADS",
            iconType = CourseIconType.TABLE,
            onClick = { onBadgeClick("ADS") },
            modifier = Modifier.testTag("badge_ads")
          )
        }

        // Bottom Dark Alert Bar with Slide-to-Dismiss micro-interaction
        InteractiveDarkAlertBar(
          message = "You have 2 exams today",
          onDismiss = onDismiss
        )
      }
    }
  }
}

enum class CourseIconType {
  BRACKETS,
  TABLE
}

@Composable
fun CourseBadge(
  tag: String,
  iconType: CourseIconType,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(16.dp))
      .border(
        width = 1.dp,
        color = MaterialTheme.invexColors.borderSubtle,
        shape = RoundedCornerShape(16.dp)
      )
      .background(MaterialTheme.invexColors.bgCardPrimary)
      .padding(horizontal = 18.dp, vertical = 10.dp),
    contentAlignment = Alignment.Center
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      Text(
        text = tag,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.SansSerif,
        color = MaterialTheme.invexColors.textPrimary
      )

      when (iconType) {
        CourseIconType.BRACKETS -> {
          Text(
            text = "{ }",
            fontSize = 22.sp,
            fontWeight = FontWeight.Light,
            fontFamily = FontFamily.Monospace,
            color = MaterialTheme.invexColors.textPrimary
          )
        }
        CourseIconType.TABLE -> {
          Icon(
            imageVector = Icons.Outlined.TableChart,
            contentDescription = "ADS Course Table",
            tint = MaterialTheme.invexColors.textPrimary,
            modifier = Modifier.size(24.dp)
          )
        }
      }
    }
  }
}

/**
 * Dark banner container holding the drag-to-dismiss handle and dismiss button.
 */
@Composable
fun InteractiveDarkAlertBar(
  message: String,
  onDismiss: () -> Unit,
  modifier: Modifier = Modifier
) {
  val coroutineScope = rememberCoroutineScope()
  val dragOffset = remember { Animatable(0f) }

  BoxWithConstraints(
    modifier = modifier
      .fillMaxWidth()
      .height(54.dp)
      .clip(CircleShape)
      .background(MaterialTheme.invexColors.bgDark)
      .testTag("interactive_dark_alert_bar"),
    contentAlignment = Alignment.CenterStart
  ) {
    val maxDragWidth = constraints.maxWidth.toFloat() - 150f // leaves space before close button

    // Center Alert Text
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 56.dp),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = message,
        color = MaterialTheme.invexColors.textInverse,
        style = MaterialTheme.invexTypography.bodyMedium,
        maxLines = 1
      )
    }

    // Draggable Left Article Handle
    Box(
      modifier = Modifier
        .offset { IntOffset(dragOffset.value.roundToInt(), 0) }
        .padding(start = 6.dp)
        .size(42.dp)
        .clip(CircleShape)
        .background(MaterialTheme.invexColors.bgDark)
        .border(1.dp, MaterialTheme.invexColors.borderSubtle.copy(alpha = 0.4f), CircleShape)
        .pointerInput(maxDragWidth) {
          detectHorizontalDragGestures(
            onDragEnd = {
              if (dragOffset.value > maxDragWidth * 0.55f) {
                // Swipe threshold met -> dismiss!
                coroutineScope.launch {
                  dragOffset.animateTo(maxDragWidth, spring())
                  onDismiss()
                }
              } else {
                // Snap back
                coroutineScope.launch {
                  dragOffset.animateTo(0f, spring())
                }
              }
            },
            onDragCancel = {
              coroutineScope.launch {
                dragOffset.animateTo(0f, spring())
              }
            },
            onHorizontalDrag = { change, dragAmount ->
              change.consume()
              val newOffset = (dragOffset.value + dragAmount).coerceIn(0f, maxDragWidth)
              coroutineScope.launch {
                dragOffset.snapTo(newOffset)
              }
            }
          )
        }
        .testTag("banner_drag_handle"),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = Icons.Outlined.Article,
        contentDescription = "Swipe to dismiss",
        tint = MaterialTheme.invexColors.textInverse,
        modifier = Modifier.size(22.dp)
      )
    }

    // Right Close / Dismiss Button
    IconButton(
      onClick = onDismiss,
      modifier = Modifier
        .align(Alignment.CenterEnd)
        .padding(end = 6.dp)
        .size(42.dp)
        .testTag("banner_close_button")
    ) {
      Box(
        modifier = Modifier
          .size(32.dp)
          .clip(CircleShape)
          .background(MaterialTheme.invexColors.textInverse.copy(alpha = 0.15f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Default.Close,
          contentDescription = "Dismiss Banner",
          tint = MaterialTheme.invexColors.textInverse,
          modifier = Modifier.size(18.dp)
        )
      }
    }
  }
}

@Preview(showBackground = true, backgroundColor = 0xFFF2F2F2)
@Composable
fun SwipeToDismissBannerPreview() {
  InvExTheme {
    SwipeToDismissBanner()
  }
}
