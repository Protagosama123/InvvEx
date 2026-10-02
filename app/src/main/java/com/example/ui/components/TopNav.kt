package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ui.theme.InvExTheme
import com.example.ui.theme.invexColors

/**
 * TopNav header navigation bar:
 * - Left: App Logo (InvEx brand logo)
 * - Right Action Cluster: Notification Bell and Profile/User circular buttons
 */
@Composable
fun TopNav(
  onNotificationsClick: () -> Unit = {},
  onProfileClick: () -> Unit = {},
  hasUnreadNotifications: Boolean = true,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 12.dp)
      .testTag("top_navigation_bar"),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    // Left: Brand Logo
    InvExLogo()

    // Right Action Cluster: Notification & Profile
    Row(
      horizontalArrangement = Arrangement.spacedBy(12.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      // Notification Bell in circular white container
      Box(
        modifier = Modifier
          .size(48.dp)
          .clip(CircleShape)
          .background(MaterialTheme.invexColors.bgCardPrimary)
          .clickable(
            role = Role.Button,
            onClickLabel = "Notifications",
            onClick = onNotificationsClick
          )
          .testTag("btn_notification"),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Outlined.Notifications,
          contentDescription = "Notifications",
          tint = MaterialTheme.invexColors.textPrimary,
          modifier = Modifier.size(24.dp)
        )

        if (hasUnreadNotifications) {
          Box(
            modifier = Modifier
              .align(Alignment.TopEnd)
              .padding(top = 10.dp, end = 10.dp)
              .size(8.dp)
              .clip(CircleShape)
              .background(MaterialTheme.invexColors.dangerRed)
          )
        }
      }

      // Profile/User in circular white container
      Box(
        modifier = Modifier
          .size(48.dp)
          .clip(CircleShape)
          .background(MaterialTheme.invexColors.bgCardPrimary)
          .clickable(
            role = Role.Button,
            onClickLabel = "Profile",
            onClick = onProfileClick
          )
          .testTag("btn_profile"),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = Icons.Outlined.PersonOutline,
          contentDescription = "User Profile",
          tint = MaterialTheme.invexColors.textPrimary,
          modifier = Modifier.size(24.dp)
        )
      }
    }
  }
}

@Preview(showBackground = true, backgroundColor = 0xFFF2F2F2)
@Composable
fun TopNavPreview() {
  InvExTheme {
    TopNav()
  }
}
