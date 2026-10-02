package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.InvExTheme
import com.example.ui.theme.invexColors
import com.example.ui.theme.invexDimens

enum class NavigationTab(val label: String, val icon: ImageVector, val tag: String) {
  HOME("Home", Icons.Outlined.Menu, "nav_tab_home"),
  EXAM("Exam", Icons.Outlined.Badge, "nav_tab_exam"),
  SCHEDULE("Schedule", Icons.Outlined.Folder, "nav_tab_schedule"),
  USER("User", Icons.Outlined.AccountCircle, "nav_tab_user")
}

/**
 * Bottom Navigation Bar (BottomBar):
 * - Fixed bottom container with 4 navigation tabs: Home, Exam, Schedule, User
 * - Highlights active tab with bold typography and dark primary accent
 */
@Composable
fun BottomBar(
  currentTab: NavigationTab = NavigationTab.HOME,
  onTabSelected: (NavigationTab) -> Unit = {},
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp, vertical = 8.dp)
      .testTag("bottom_navigation_bar"),
    contentAlignment = Alignment.Center
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .height(72.dp)
        .clip(RoundedCornerShape(MaterialTheme.invexDimens.radiusLg))
        .background(MaterialTheme.invexColors.bgCardSecondary.copy(alpha = 0.85f))
        .padding(horizontal = 8.dp),
      horizontalArrangement = Arrangement.SpaceAround,
      verticalAlignment = Alignment.CenterVertically
    ) {
      NavigationTab.values().forEach { tab ->
        val isSelected = tab == currentTab
        val contentColor = if (isSelected) {
          MaterialTheme.invexColors.textPrimary
        } else {
          MaterialTheme.invexColors.textSecondary
        }

        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center,
          modifier = Modifier
            .weight(1f)
            .height(58.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable(
              role = Role.Tab,
              interactionSource = remember { MutableInteractionSource() },
              indication = ripple(bounded = true, color = MaterialTheme.invexColors.bgDark),
              onClick = { onTabSelected(tab) }
            )
            .testTag(tab.tag)
        ) {
          Icon(
            imageVector = tab.icon,
            contentDescription = tab.label,
            tint = contentColor,
            modifier = Modifier.size(24.dp)
          )

          Spacer(modifier = Modifier.height(4.dp))

          Text(
            text = tab.label,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = contentColor
          )
        }
      }
    }
  }
}

@Preview(showBackground = true, backgroundColor = 0xFFF2F2F2)
@Composable
fun BottomBarPreview() {
  InvExTheme {
    BottomBar()
  }
}
