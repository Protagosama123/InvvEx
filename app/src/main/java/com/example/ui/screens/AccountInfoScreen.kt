package com.example.ui.screens

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.InvExTheme
import com.example.ui.theme.invexColors
import com.example.ui.theme.invexDimens
import com.example.ui.theme.invexTypography
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Screen: Settings / Account Information
 * Displays editable/pre-populated fields for Name, Roll Number, and Phone Number
 * with SAVE and CANCEL actions.
 */
@Composable
fun AccountInfoScreen(
  onBackClick: () -> Unit = {},
  onCancelClick: () -> Unit = onBackClick,
  onSaveSuccess: () -> Unit = onBackClick,
  initialName: String = "John Doe",
  initialRollNumber: String = "CS2026-890",
  initialPhoneNumber: String = "+1 (555) 019-2834",
  modifier: Modifier = Modifier
) {
  BackHandler { onBackClick() }

  val coroutineScope = rememberCoroutineScope()
  val snackbarHostState = remember { SnackbarHostState() }

  var name by remember { mutableStateOf(initialName) }
  var rollNumber by remember { mutableStateOf(initialRollNumber) }
  var phoneNumber by remember { mutableStateOf(initialPhoneNumber) }
  var isSaving by remember { mutableStateOf(false) }

  Scaffold(
    modifier = modifier
      .fillMaxSize()
      .testTag("account_info_screen"),
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
          .testTag("account_info_action_bar"),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        // SAVE Button (Solid Dark Black container)
        Button(
          onClick = {
            if (name.isBlank()) {
              coroutineScope.launch { snackbarHostState.showSnackbar("Name cannot be empty.") }
              return@Button
            }
            isSaving = true
            coroutineScope.launch {
              delay(800)
              isSaving = false
              snackbarHostState.showSnackbar("Account information updated successfully!")
              delay(300)
              onSaveSuccess()
            }
          },
          enabled = !isSaving,
          modifier = Modifier
            .weight(1f)
            .height(54.dp)
            .testTag("btn_account_save"),
          colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.invexColors.bgDark,
            contentColor = MaterialTheme.invexColors.textInverse
          ),
          shape = RoundedCornerShape(MaterialTheme.invexDimens.radiusMd)
        ) {
          if (isSaving) {
            CircularProgressIndicator(
              modifier = Modifier.size(22.dp),
              color = MaterialTheme.invexColors.textInverse,
              strokeWidth = 2.5.dp
            )
          } else {
            Text(
              text = "SAVE",
              fontSize = 17.sp,
              fontWeight = FontWeight.Bold,
              letterSpacing = 0.5.sp
            )
          }
        }

        // CANCEL Button (Grey container)
        Box(
          modifier = Modifier
            .weight(1f)
            .height(54.dp)
            .clip(RoundedCornerShape(MaterialTheme.invexDimens.radiusMd))
            .background(Color(0xFFE0E0E0))
            .clickable(
              role = Role.Button,
              enabled = !isSaving,
              onClick = onCancelClick
            )
            .testTag("btn_account_cancel"),
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
            .testTag("btn_account_back")
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
            text = "/ Account Information",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            color = MaterialTheme.invexColors.textPrimary,
            lineHeight = 30.sp
          )
        }
      }

      Spacer(modifier = Modifier.height(28.dp))

      // 1. Name Field
      AccountInputField(
        label = "Name",
        value = name,
        onValueChange = { name = it },
        testTag = "input_account_name"
      )

      Spacer(modifier = Modifier.height(20.dp))

      // 2. Roll Number Field
      AccountInputField(
        label = "Roll Number",
        value = rollNumber,
        onValueChange = { rollNumber = it },
        testTag = "input_account_roll_number"
      )

      Spacer(modifier = Modifier.height(20.dp))

      // 3. Phone Number Field
      AccountInputField(
        label = "Phone Number",
        value = phoneNumber,
        onValueChange = { phoneNumber = it },
        testTag = "input_account_phone_number"
      )

      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}

/**
 * Clean input field matching the rounded grey outline container in Figma screenshot
 */
@Composable
fun AccountInputField(
  label: String,
  value: String,
  onValueChange: (String) -> Unit,
  testTag: String
) {
  Column(modifier = Modifier.fillMaxWidth()) {
    Text(
      text = label,
      fontSize = 20.sp,
      fontWeight = FontWeight.Bold,
      fontFamily = FontFamily.SansSerif,
      color = MaterialTheme.invexColors.textPrimary,
      modifier = Modifier.padding(bottom = 8.dp)
    )

    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(52.dp)
        .clip(RoundedCornerShape(14.dp))
        .background(Color(0xFFE8E8E8))
        .border(1.dp, Color(0xFFD0D0D0), RoundedCornerShape(14.dp))
        .padding(horizontal = 16.dp)
        .testTag(testTag),
      contentAlignment = Alignment.CenterStart
    ) {
      BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(
          fontSize = 17.sp,
          fontWeight = FontWeight.SemiBold,
          fontFamily = FontFamily.SansSerif,
          color = MaterialTheme.invexColors.textPrimary
        ),
        cursorBrush = SolidColor(MaterialTheme.invexColors.textPrimary),
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
      )
    }
  }
}

@Preview(
  name = "AccountInfoScreen Light Mode",
  showBackground = true,
  device = "spec:width=411dp,height=891dp,dpi=420"
)
@Composable
fun AccountInfoScreenPreview() {
  InvExTheme(darkTheme = false) {
    AccountInfoScreen()
  }
}
