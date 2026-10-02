package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import kotlinx.coroutines.launch

/**
 * Screen: Settings / Notifications
 * Configures exam notifications, email alerts toggle, and channel checkboxes.
 */
@Composable
fun NotificationsScreen(
  onBackClick: () -> Unit = {},
  onCancelClick: () -> Unit = onBackClick,
  onSavePreferences: () -> Unit = onBackClick,
  modifier: Modifier = Modifier
) {
  BackHandler { onBackClick() }

  val coroutineScope = rememberCoroutineScope()
  val snackbarHostState = remember { SnackbarHostState() }

  // Preferences State
  var emailNotificationEnabled by remember { mutableStateOf(false) }
  var newExamsEnabled by remember { mutableStateOf(true) }
  var notificationChannelsEnabled by remember { mutableStateOf(false) }
  var feedbackNotificationsEnabled by remember { mutableStateOf(false) }

  Scaffold(
    modifier = modifier
      .fillMaxSize()
      .testTag("notifications_screen"),
    containerColor = MaterialTheme.invexColors.bgScreen,
    contentWindowInsets = WindowInsets(0, 0, 0, 0),
    snackbarHost = { SnackbarHost(snackbarHostState) },
    bottomBar = {
      // Bottom Sticky Action Bar: SAVE (Left - Dark) & CANCEL (Right - Grey)
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .background(MaterialTheme.invexColors.bgScreen)
          .padding(
            start = 20.dp,
            end = 20.dp,
            top = 12.dp,
            bottom = 12.dp + WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
          )
          .testTag("notifications_action_bar"),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        // SAVE Button
        Button(
          onClick = {
            coroutineScope.launch {
              snackbarHostState.showSnackbar("Notification preferences saved.")
            }
            onSavePreferences()
          },
          modifier = Modifier
            .weight(1f)
            .height(54.dp)
            .testTag("btn_notifications_save"),
          colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.invexColors.bgDark,
            contentColor = MaterialTheme.invexColors.textInverse
          ),
          shape = RoundedCornerShape(MaterialTheme.invexDimens.radiusMd)
        ) {
          Text(
            text = "SAVE",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp
          )
        }

        // CANCEL Button
        Box(
          modifier = Modifier
            .weight(1f)
            .height(54.dp)
            .clip(RoundedCornerShape(MaterialTheme.invexDimens.radiusMd))
            .background(Color(0xFFE0E0E0))
            .clickable(
              role = Role.Button,
              onClick = onCancelClick
            )
            .testTag("btn_notifications_cancel"),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "CANCEL",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.5.sp,
            color = MaterialTheme.invexColors.textPrimary
          )
        }
      }
    }
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 20.dp)
    ) {
      Spacer(
        modifier = Modifier.height(
          WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 8.dp
        )
      )

      // Header Section: Back Chevron + Breadcrumb Title
      Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth()
      ) {
        IconButton(
          onClick = onBackClick,
          modifier = Modifier
            .padding(top = 2.dp)
            .size(40.dp)
            .testTag("btn_notifications_back")
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            contentDescription = "Navigate Back",
            tint = MaterialTheme.invexColors.textPrimary,
            modifier = Modifier.size(36.dp)
          )
        }

        Spacer(modifier = Modifier.width(4.dp))

        Column {
          Text(
            text = "Settings",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            color = MaterialTheme.invexColors.textPrimary,
            lineHeight = 34.sp
          )
          Text(
            text = "/ Notifications",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            color = MaterialTheme.invexColors.textPrimary,
            lineHeight = 30.sp
          )
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = "Get notification about exams, you can\nturn that off any time.",
        fontSize = 16.sp,
        color = Color(0xFF666666),
        lineHeight = 22.sp
      )

      Spacer(modifier = Modifier.height(24.dp))

      // 1. Email Notification with custom pill toggle
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("setting_email_notification")
      ) {
        Text(
          text = "Email Notification",
          fontSize = 18.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.SansSerif,
          color = MaterialTheme.invexColors.textPrimary
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = "Invex can send you email notification for new exams.",
          fontSize = 14.sp,
          color = Color(0xFF666666),
          lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        CustomSwitchToggle(
          checked = emailNotificationEnabled,
          onCheckedChange = { emailNotificationEnabled = it },
          testTag = "toggle_email_notification"
        )
      }

      Spacer(modifier = Modifier.height(24.dp))

      // 2. New Exams and Updates Setting (Checked by default)
      NotificationCheckboxRow(
        title = "New Exams and Updates Setting",
        description = "The latest info about the new exams, significant changes in exam schedule",
        checked = newExamsEnabled,
        onCheckedChange = { newExamsEnabled = it },
        testTag = "checkbox_new_exams"
      )

      Spacer(modifier = Modifier.height(24.dp))

      // 3. Notification Channels
      NotificationCheckboxRow(
        title = "Notification Channels",
        description = "Choose Preferred notification channels, including sms, mobile app, to receive updates conveniently.",
        checked = notificationChannelsEnabled,
        onCheckedChange = { notificationChannelsEnabled = it },
        testTag = "checkbox_notification_channels"
      )

      Spacer(modifier = Modifier.height(24.dp))

      // 4. Feedback Notifications
      NotificationCheckboxRow(
        title = "Feedback Notifications",
        description = "Receive notifications for app feedback, reviews or surveys to improvise user experience.",
        checked = feedbackNotificationsEnabled,
        onCheckedChange = { feedbackNotificationsEnabled = it },
        testTag = "checkbox_feedback_notifications"
      )

      Spacer(modifier = Modifier.height(30.dp))
    }
  }
}

/**
 * Custom Pill Switch Toggle matching Figma screenshot
 */
@Composable
fun CustomSwitchToggle(
  checked: Boolean,
  onCheckedChange: (Boolean) -> Unit,
  testTag: String
) {
  val horizontalBias by animateFloatAsState(
    targetValue = if (checked) 1f else -1f,
    label = "SwitchToggleThumbAnimation"
  )

  Box(
    modifier = Modifier
      .width(54.dp)
      .height(30.dp)
      .clip(CircleShape)
      .background(Color(0xFFDCDCDC))
      .border(1.5.dp, Color.Black, CircleShape)
      .clickable(
        role = Role.Switch,
        onClick = { onCheckedChange(!checked) }
      )
      .padding(horizontal = 3.dp)
      .testTag(testTag),
    contentAlignment = BiasAlignment(horizontalBias, 0f)
  ) {
    Box(
      modifier = Modifier
        .size(22.dp)
        .clip(CircleShape)
        .background(Color.White)
        .border(1.5.dp, Color.Black, CircleShape)
    )
  }
}

/**
 * Notification Checkbox row with title, description, and square checkbox
 */
@Composable
fun NotificationCheckboxRow(
  title: String,
  description: String,
  checked: Boolean,
  onCheckedChange: (Boolean) -> Unit,
  testTag: String
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clickable(
        role = Role.Checkbox,
        onClick = { onCheckedChange(!checked) }
      )
      .testTag(testTag),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.Top
  ) {
    Column(modifier = Modifier.weight(1f)) {
      Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.SansSerif,
        color = MaterialTheme.invexColors.textPrimary
      )

      Spacer(modifier = Modifier.height(4.dp))

      Text(
        text = description,
        fontSize = 14.sp,
        color = Color(0xFF666666),
        lineHeight = 20.sp
      )
    }

    Spacer(modifier = Modifier.width(16.dp))

    // Square Checkbox outline matching Figma
    Box(
      modifier = Modifier
        .padding(top = 2.dp)
        .size(26.dp)
        .border(2.5.dp, Color.Black, RoundedCornerShape(5.dp))
        .background(Color.Transparent),
      contentAlignment = Alignment.Center
    ) {
      if (checked) {
        Icon(
          imageVector = Icons.Default.Check,
          contentDescription = "Checked",
          tint = Color.Black,
          modifier = Modifier.size(20.dp)
        )
      }
    }
  }
}

@Preview(
  name = "NotificationsScreen Light Mode",
  showBackground = true,
  device = "spec:width=411dp,height=891dp,dpi=420"
)
@Composable
fun NotificationsScreenPreview() {
  InvExTheme(darkTheme = false) {
    NotificationsScreen()
  }
}
