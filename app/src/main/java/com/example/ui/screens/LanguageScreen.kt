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

enum class AppLanguage(val code: String, val displayName: String) {
  ENGLISH("en", "English"),
  HINDI("hi", "Hindi")
}

/**
 * Screen: Settings / Language Selection
 * Single-select radio list between English and Hindi matching Figma reference.
 */
@Composable
fun LanguageScreen(
  onBackClick: () -> Unit = {},
  onCancelClick: () -> Unit = onBackClick,
  onSaveLanguage: (AppLanguage) -> Unit = {},
  initialLanguage: AppLanguage = AppLanguage.ENGLISH,
  modifier: Modifier = Modifier
) {
  BackHandler { onBackClick() }

  val coroutineScope = rememberCoroutineScope()
  val snackbarHostState = remember { SnackbarHostState() }
  var selectedLanguage by remember { mutableStateOf(initialLanguage) }

  Scaffold(
    modifier = modifier
      .fillMaxSize()
      .testTag("language_screen"),
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
          .testTag("language_action_bar"),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        // SAVE Button
        Button(
          onClick = {
            onSaveLanguage(selectedLanguage)
            coroutineScope.launch {
              snackbarHostState.showSnackbar("Language set to ${selectedLanguage.displayName}.")
            }
          },
          modifier = Modifier
            .weight(1f)
            .height(54.dp)
            .testTag("btn_language_save"),
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
            .testTag("btn_language_cancel"),
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
            .testTag("btn_language_back")
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
            text = "/ Language",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            color = MaterialTheme.invexColors.textPrimary,
            lineHeight = 30.sp
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = "Choose your preferred\nLanguage",
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
        color = Color(0xFF666666),
        lineHeight = 26.sp
      )

      Spacer(modifier = Modifier.height(28.dp))

      // 1. English Option Tile
      LanguageOptionTile(
        language = AppLanguage.ENGLISH,
        isSelected = selectedLanguage == AppLanguage.ENGLISH,
        onClick = { selectedLanguage = AppLanguage.ENGLISH },
        testTag = "language_tile_english"
      )

      Spacer(modifier = Modifier.height(16.dp))

      // 2. Hindi Option Tile
      LanguageOptionTile(
        language = AppLanguage.HINDI,
        isSelected = selectedLanguage == AppLanguage.HINDI,
        onClick = { selectedLanguage = AppLanguage.HINDI },
        testTag = "language_tile_hindi"
      )

      Spacer(modifier = Modifier.height(28.dp))
    }
  }
}

/**
 * Language Option Tile matching Figma reference:
 * - Rounded grey pill card
 * - Square checkbox icon (checked [✓] or unchecked [ ])
 * - Bold language label
 */
@Composable
fun LanguageOptionTile(
  language: AppLanguage,
  isSelected: Boolean,
  onClick: () -> Unit,
  testTag: String
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .height(64.dp)
      .clip(RoundedCornerShape(16.dp))
      .background(Color(0xFFDCDCDC))
      .clickable(
        role = Role.RadioButton,
        onClick = onClick
      )
      .padding(horizontal = 20.dp)
      .testTag(testTag),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // Checkbox Square Box
    Box(
      modifier = Modifier
        .size(26.dp)
        .border(2.5.dp, Color.Black, RoundedCornerShape(5.dp))
        .background(Color.Transparent),
      contentAlignment = Alignment.Center
    ) {
      if (isSelected) {
        Icon(
          imageVector = Icons.Default.Check,
          contentDescription = "Selected",
          tint = Color.Black,
          modifier = Modifier.size(20.dp)
        )
      }
    }

    Text(
      text = language.displayName,
      fontSize = 22.sp,
      fontWeight = FontWeight.Bold,
      fontFamily = FontFamily.SansSerif,
      color = Color.Black
    )
  }
}

@Preview(
  name = "LanguageScreen Light Mode",
  showBackground = true,
  device = "spec:width=411dp,height=891dp,dpi=420"
)
@Composable
fun LanguageScreenPreview() {
  InvExTheme(darkTheme = false) {
    LanguageScreen()
  }
}
