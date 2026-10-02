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

enum class AppThemeMode(val label: String) {
  LIGHT("Light"),
  DARK("Dark"),
  SYSTEM("System")
}

/**
 * Screen: Settings / Theme Selection
 * Allows toggling between Light, Dark, and System theme modes
 * with Figma-matching wireframe mockups.
 */
@Composable
fun ThemeScreen(
  onBackClick: () -> Unit = {},
  onCancelClick: () -> Unit = onBackClick,
  onSaveTheme: (AppThemeMode) -> Unit = {},
  initialTheme: AppThemeMode = AppThemeMode.LIGHT,
  modifier: Modifier = Modifier
) {
  BackHandler { onBackClick() }

  val coroutineScope = rememberCoroutineScope()
  val snackbarHostState = remember { SnackbarHostState() }
  var selectedTheme by remember { mutableStateOf(initialTheme) }

  Scaffold(
    modifier = modifier
      .fillMaxSize()
      .testTag("theme_screen"),
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
          .testTag("theme_action_bar"),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        // SAVE Button
        Button(
          onClick = {
            onSaveTheme(selectedTheme)
            coroutineScope.launch {
              snackbarHostState.showSnackbar("Applied ${selectedTheme.label} theme.")
            }
          },
          modifier = Modifier
            .weight(1f)
            .height(54.dp)
            .testTag("btn_theme_save"),
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
            .testTag("btn_theme_cancel"),
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
            .testTag("btn_theme_back")
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
            text = "/ Theme",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            color = MaterialTheme.invexColors.textPrimary,
            lineHeight = 30.sp
          )
        }
      }

      Spacer(modifier = Modifier.height(24.dp))

      // 1. Light Theme Option
      ThemeOptionCard(
        label = "Light",
        mode = AppThemeMode.LIGHT,
        isSelected = selectedTheme == AppThemeMode.LIGHT,
        onClick = { selectedTheme = AppThemeMode.LIGHT }
      )

      Spacer(modifier = Modifier.height(20.dp))

      // 2. Dark Theme Option
      ThemeOptionCard(
        label = "Dark",
        mode = AppThemeMode.DARK,
        isSelected = selectedTheme == AppThemeMode.DARK,
        onClick = { selectedTheme = AppThemeMode.DARK }
      )

      Spacer(modifier = Modifier.height(20.dp))

      // 3. System Theme Option
      ThemeOptionCard(
        label = "System",
        mode = AppThemeMode.SYSTEM,
        isSelected = selectedTheme == AppThemeMode.SYSTEM,
        onClick = { selectedTheme = AppThemeMode.SYSTEM }
      )

      Spacer(modifier = Modifier.height(28.dp))
    }
  }
}

/**
 * Theme selection wireframe mockup card matching Figma screenshot
 */
@Composable
fun ThemeOptionCard(
  label: String,
  mode: AppThemeMode,
  isSelected: Boolean,
  onClick: () -> Unit
) {
  val borderColor = if (isSelected) MaterialTheme.invexColors.textPrimary else Color(0xFFC8C8C8)
  val borderWidth = if (isSelected) 2.5.dp else 1.dp

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("theme_card_${label.lowercase()}")
  ) {
    Text(
      text = label,
      fontSize = 20.sp,
      fontWeight = FontWeight.Bold,
      fontFamily = FontFamily.SansSerif,
      color = if (isSelected) MaterialTheme.invexColors.textPrimary else Color(0xFF666666),
      modifier = Modifier.padding(bottom = 8.dp)
    )

    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(130.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(
          when (mode) {
            AppThemeMode.LIGHT -> Color(0xFFF2ECE9).copy(alpha = 0.5f)
            AppThemeMode.DARK -> Color(0xFFE8E8E8)
            AppThemeMode.SYSTEM -> Color(0xFFECECEC)
          }
        )
        .border(borderWidth, borderColor, RoundedCornerShape(16.dp))
        .clickable(
          role = Role.RadioButton,
          onClick = onClick
        )
        .padding(14.dp)
    ) {
      Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        // Top header wireframe bar
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(28.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(Color.White)
        )

        // Bottom two-column area: Left avatar box + Right rows
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          // Left square block
          Box(
            modifier = Modifier
              .width(80.dp)
              .fillMaxSize()
              .clip(RoundedCornerShape(6.dp))
              .background(Color.White)
          )

          // Right 2 horizontal rows
          Column(
            modifier = Modifier
              .weight(1f)
              .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(6.dp))
                .background(Color.White)
            )
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(6.dp))
                .background(Color.White)
            )
          }
        }
      }
    }
  }
}

@Preview(
  name = "ThemeScreen Light Mode",
  showBackground = true,
  device = "spec:width=411dp,height=891dp,dpi=420"
)
@Composable
fun ThemeScreenPreview() {
  InvExTheme(darkTheme = false) {
    ThemeScreen()
  }
}
