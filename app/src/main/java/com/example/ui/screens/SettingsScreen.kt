package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.BottomBar
import com.example.ui.components.NavigationTab
import com.example.ui.theme.InvExTheme
import com.example.ui.theme.invexColors
import com.example.ui.theme.invexDimens
import com.example.ui.theme.invexTypography
import kotlinx.coroutines.launch

enum class SettingsDestination {
  ROOT,
  ACCOUNT_INFO,
  THEME,
  LANGUAGE,
  NOTIFICATIONS
}

/**
 * Screen 1: Main Settings Screen (Tab Bar - Settings)
 * Displays Profile hero block, General settings group, Support group,
 * destructive Log Out action with confirmation dialog, and BottomBar with User tab active.
 */
@Composable
fun SettingsScreen(
  onNavigateToAccount: () -> Unit = {},
  onNavigateToTheme: () -> Unit = {},
  onNavigateToLanguage: () -> Unit = {},
  onNavigateToNotifications: () -> Unit = {},
  onNavigateToTab: (NavigationTab) -> Unit = {},
  onLogOut: () -> Unit = {},
  modifier: Modifier = Modifier
) {
  val coroutineScope = rememberCoroutineScope()
  val snackbarHostState = remember { SnackbarHostState() }
  var showLogoutConfirmDialog by remember { mutableStateOf(false) }

  Scaffold(
    modifier = modifier
      .fillMaxSize()
      .testTag("settings_screen"),
    containerColor = MaterialTheme.invexColors.bgScreen,
    contentWindowInsets = WindowInsets(0, 0, 0, 0),
    snackbarHost = { SnackbarHost(snackbarHostState) },
    bottomBar = {
      // BottomBar with User tab active
      BottomBar(
        currentTab = NavigationTab.USER,
        onTabSelected = onNavigateToTab,
        modifier = Modifier.padding(
          bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
        )
      )
    }
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 24.dp)
    ) {
      Spacer(
        modifier = Modifier.height(
          WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 8.dp
        )
      )

      // 1. Header Section: Title "Settings"
      Text(
        text = "Settings",
        fontSize = 34.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.SansSerif,
        color = MaterialTheme.invexColors.textPrimary
      )

      Spacer(modifier = Modifier.height(20.dp))

      // Profile Hero Placeholder Block matching Figma (large rounded grey block)
      Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
      ) {
        Box(
          modifier = Modifier
            .size(160.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFFDCDCDC))
            .testTag("profile_hero_block")
        )
      }

      Spacer(modifier = Modifier.height(28.dp))

      // 2. "General" Settings Group
      Text(
        text = "General",
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = FontFamily.SansSerif,
        color = Color(0xFF777777),
        modifier = Modifier.padding(bottom = 8.dp)
      )

      // Account Information Row
      SettingRowItem(
        icon = Icons.Outlined.PersonOutline,
        label = "Account Information",
        onClick = onNavigateToAccount,
        testTag = "setting_item_account"
      )

      Spacer(modifier = Modifier.height(6.dp))

      // Theme Row
      SettingRowItem(
        icon = Icons.Outlined.Palette,
        label = "Theme",
        onClick = onNavigateToTheme,
        testTag = "setting_item_theme"
      )

      Spacer(modifier = Modifier.height(6.dp))

      // Language Row
      SettingRowItem(
        icon = Icons.Outlined.Language,
        label = "Language",
        onClick = onNavigateToLanguage,
        testTag = "setting_item_language"
      )

      Spacer(modifier = Modifier.height(6.dp))

      // Notifications Row (Highlighted with light grey pill container per Figma)
      SettingRowItem(
        icon = Icons.Outlined.Notifications,
        label = "Notifications",
        isHighlighted = true,
        onClick = onNavigateToNotifications,
        testTag = "setting_item_notifications"
      )

      Spacer(modifier = Modifier.height(22.dp))

      // 3. "Support" Settings Group
      Text(
        text = "Support",
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        fontFamily = FontFamily.SansSerif,
        color = Color(0xFF777777),
        modifier = Modifier.padding(bottom = 8.dp)
      )

      // Report a Issue
      Text(
        text = "Report a Issue",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.SansSerif,
        color = MaterialTheme.invexColors.textPrimary,
        modifier = Modifier
          .fillMaxWidth()
          .clickable {
            coroutineScope.launch {
              snackbarHostState.showSnackbar("Opening issue reporting ticket portal...")
            }
          }
          .padding(vertical = 10.dp)
          .testTag("support_report_issue")
      )

      // FAQ
      Text(
        text = "FAQ",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.SansSerif,
        color = MaterialTheme.invexColors.textPrimary,
        modifier = Modifier
          .fillMaxWidth()
          .clickable {
            coroutineScope.launch {
              snackbarHostState.showSnackbar("Opening Frequently Asked Questions...")
            }
          }
          .padding(vertical = 8.dp)
          .testTag("support_faq")
      )

      Spacer(modifier = Modifier.height(18.dp))

      // 4. Destructive Action / LOG OUT
      Text(
        text = "LOG OUT",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.SansSerif,
        color = MaterialTheme.invexColors.dangerRed,
        modifier = Modifier
          .clickable { showLogoutConfirmDialog = true }
          .padding(vertical = 12.dp)
          .testTag("btn_logout")
      )

      Spacer(modifier = Modifier.height(24.dp))
    }
  }

  // Logout Confirmation Dialog
  if (showLogoutConfirmDialog) {
    AlertDialog(
      onDismissRequest = { showLogoutConfirmDialog = false },
      title = {
        Text("Are you sure you want to log out?", fontWeight = FontWeight.Bold)
      },
      text = {
        Text("You will need to sign in again to view your admit cards, schedules, and examination results.")
      },
      confirmButton = {
        TextButton(
          onClick = {
            showLogoutConfirmDialog = false
            onLogOut()
            coroutineScope.launch {
              snackbarHostState.showSnackbar("Logged out successfully.")
            }
          }
        ) {
          Text("Logout", fontWeight = FontWeight.Bold, color = MaterialTheme.invexColors.dangerRed)
        }
      },
      dismissButton = {
        TextButton(onClick = { showLogoutConfirmDialog = false }) {
          Text("Cancel", fontWeight = FontWeight.Bold, color = MaterialTheme.invexColors.textPrimary)
        }
      }
    )
  }
}

/**
 * Settings Navigation Row Item
 */
@Composable
fun SettingRowItem(
  icon: ImageVector,
  label: String,
  onClick: () -> Unit,
  testTag: String,
  isHighlighted: Boolean = false,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .height(54.dp)
      .clip(RoundedCornerShape(16.dp))
      .background(if (isHighlighted) Color(0xFFE8E8E8) else Color.Transparent)
      .clickable(
        role = Role.Button,
        onClick = onClick
      )
      .padding(horizontal = if (isHighlighted) 14.dp else 2.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = MaterialTheme.invexColors.textPrimary,
        modifier = Modifier.size(24.dp)
      )

      Text(
        text = label,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.SansSerif,
        color = MaterialTheme.invexColors.textPrimary
      )
    }

    Icon(
      imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
      contentDescription = "Open $label",
      tint = MaterialTheme.invexColors.textPrimary,
      modifier = Modifier.size(24.dp)
    )
  }
}

@Preview(
  name = "SettingsScreen Light Mode",
  showBackground = true,
  device = "spec:width=411dp,height=891dp,dpi=420"
)
@Composable
fun SettingsScreenPreview() {
  InvExTheme(darkTheme = false) {
    SettingsScreen()
  }
}
