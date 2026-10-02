package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.VolumeUp
import androidx.compose.material.icons.outlined.WarningAmber
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.InvExTheme
import com.example.ui.theme.invexColors
import com.example.ui.theme.invexDimens
import com.example.ui.theme.invexTypography

data class NoticeItemData(
  val id: String,
  val text: String,
  val isWarning: Boolean = false
)

/**
 * Reusable section for "Broadcasts" or "Circulars":
 * - Header title (22sp bold)
 * - White card container (radius-lg 24dp)
 * - Rounded outline boxes containing leading icon, truncated message, trailing "view >"
 * - Centered "View all ..." pill button
 */
@Composable
fun NoticeSection(
  title: String,
  items: List<NoticeItemData>,
  viewAllLabel: String,
  onItemClick: (NoticeItemData) -> Unit = {},
  onViewAllClick: () -> Unit = {},
  testTagPrefix: String = "notice",
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 6.dp)
      .testTag("${testTagPrefix}_section")
  ) {
    // Section Header
    Text(
      text = title,
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
          .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        items.take(3).forEachIndexed { index, item ->
          NoticeItemRow(
            item = item,
            onClick = { onItemClick(item) },
            modifier = Modifier.testTag("${testTagPrefix}_item_$index")
          )
        }

        // Faded preview bottom row with centered "View all ..." pill button
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
          contentAlignment = Alignment.Center
        ) {
          // Faded background item
          if (items.size > 3) {
            NoticeItemRow(
              item = items[3],
              onClick = {},
              modifier = Modifier
                .fillMaxWidth()
                .alpha(0.22f)
            )
          } else {
            NoticeItemRow(
              item = NoticeItemData("preview", "Lorem ipsum dolor sit amet, consectetur elit..."),
              onClick = {},
              modifier = Modifier
                .fillMaxWidth()
                .alpha(0.22f)
            )
          }

          // Centered Pill Button
          Box(
            modifier = Modifier
              .clip(CircleShape)
              .background(MaterialTheme.invexColors.bgCardPrimary)
              .border(1.dp, MaterialTheme.invexColors.borderSubtle, CircleShape)
              .clickable(
                role = Role.Button,
                onClick = onViewAllClick
              )
              .padding(horizontal = 24.dp, vertical = 8.dp)
              .testTag("btn_${testTagPrefix}_view_all"),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = viewAllLabel,
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.invexColors.textPrimary
            )
          }
        }
      }
    }
  }
}

@Composable
fun NoticeItemRow(
  item: NoticeItemData,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(MaterialTheme.invexDimens.radiusSm))
      .border(
        width = 1.dp,
        color = MaterialTheme.invexColors.borderSubtle,
        shape = RoundedCornerShape(MaterialTheme.invexDimens.radiusSm)
      )
      .background(MaterialTheme.invexColors.bgCardPrimary)
      .clickable(
        role = Role.Button,
        onClick = onClick
      )
      .padding(horizontal = 12.dp, vertical = 10.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.weight(1f)
    ) {
      Icon(
        imageVector = if (item.isWarning) Icons.Outlined.WarningAmber else Icons.Outlined.VolumeUp,
        contentDescription = if (item.isWarning) "Warning" else "Announcement",
        tint = MaterialTheme.invexColors.textPrimary,
        modifier = Modifier.size(20.dp)
      )

      Spacer(modifier = Modifier.width(10.dp))

      Text(
        text = item.text,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        color = MaterialTheme.invexColors.textPrimary,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
      )
    }

    Spacer(modifier = Modifier.width(8.dp))

    Text(
      text = "view >",
      fontSize = 12.sp,
      fontWeight = FontWeight.Normal,
      color = MaterialTheme.invexColors.textSecondary
    )
  }
}

@Preview(showBackground = true, backgroundColor = 0xFFF2F2F2)
@Composable
fun NoticeSectionPreview() {
  InvExTheme {
    NoticeSection(
      title = "Broadcasts",
      items = listOf(
        NoticeItemData("1", "Lorem ipsum dolor sit amet, consectetur elit..."),
        NoticeItemData("2", "Lorem ipsum dolor sit amet, consectetur elit...", isWarning = true),
        NoticeItemData("3", "Lorem ipsum dolor sit amet, consectetur elit...")
      ),
      viewAllLabel = "View all Broadcasts"
    )
  }
}
