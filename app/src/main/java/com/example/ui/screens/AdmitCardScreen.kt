package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
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
import kotlinx.coroutines.launch

data class ExamScheduleEntry(
  val course: String,
  val date: String,
  val shift: String
)

/**
 * Screen 1: Digital Admit Card Screen (Service - Admit Card)
 * Matching exact Figma specifications and UI tokens.
 */
@Composable
fun AdmitCardScreen(
  onBackClick: () -> Unit = {},
  modifier: Modifier = Modifier
) {
  BackHandler { onBackClick() }

  val coroutineScope = rememberCoroutineScope()
  val snackbarHostState = remember { SnackbarHostState() }
  var showVerifyDialog by remember { mutableStateOf(false) }

  val scheduleEntries = remember {
    listOf(
      ExamScheduleEntry("Lorem Ipsum", "01/01/2000", "B"),
      ExamScheduleEntry("Lorem Ipsum", "01/01/2000", "B"),
      ExamScheduleEntry("Lorem Ipsum", "01/01/2000", "B"),
      ExamScheduleEntry("Lorem Ipsum", "01/01/2000", "B"),
      ExamScheduleEntry("Lorem Ipsum", "01/01/2000", "B")
    )
  }

  Scaffold(
    modifier = modifier
      .fillMaxSize()
      .testTag("admit_card_screen"),
    containerColor = MaterialTheme.invexColors.bgScreen,
    contentWindowInsets = WindowInsets(0, 0, 0, 0),
    snackbarHost = { SnackbarHost(snackbarHostState) },
    bottomBar = {
      // Bottom Sticky Action Buttons
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
          .testTag("admit_card_actions"),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        // Secondary Action: "Verify the Card" (Light cream-tint container)
        Box(
          modifier = Modifier
            .weight(1f)
            .height(54.dp)
            .clip(RoundedCornerShape(MaterialTheme.invexDimens.radiusMd))
            .background(Color(0xFFF2ECE9))
            .border(1.dp, MaterialTheme.invexColors.borderSubtle, RoundedCornerShape(MaterialTheme.invexDimens.radiusMd))
            .clickable(
              role = Role.Button,
              onClick = { showVerifyDialog = true }
            )
            .testTag("btn_verify_the_card"),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "Verify the Card",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.invexColors.textPrimary
          )
        }

        // Primary Action: "Download" (Solid Black container)
        Button(
          onClick = {
            coroutineScope.launch {
              snackbarHostState.showSnackbar("Admit card PDF downloaded to device.")
            }
          },
          modifier = Modifier
            .weight(1f)
            .height(54.dp)
            .testTag("btn_download_admit_card"),
          colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.invexColors.bgDark,
            contentColor = MaterialTheme.invexColors.textInverse
          ),
          shape = RoundedCornerShape(MaterialTheme.invexDimens.radiusMd)
        ) {
          Text(
            text = "Download",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
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

      // 1. Header Section
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
      ) {
        IconButton(
          onClick = onBackClick,
          modifier = Modifier
            .size(40.dp)
            .testTag("btn_admit_card_back")
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            contentDescription = "Navigate Back",
            tint = MaterialTheme.invexColors.textPrimary,
            modifier = Modifier.size(36.dp)
          )
        }

        Spacer(modifier = Modifier.width(4.dp))

        Text(
          text = "Admit Card",
          fontSize = 32.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.SansSerif,
          color = MaterialTheme.invexColors.textPrimary
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = "Your admit card is ready",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.invexColors.textPrimary
      )

      Spacer(modifier = Modifier.height(4.dp))

      Text(
        text = "Verify the card with official records or download right away.",
        fontSize = 14.sp,
        color = MaterialTheme.invexColors.textSecondary,
        lineHeight = 20.sp
      )

      Spacer(modifier = Modifier.height(20.dp))

      // 2. Digital Admit Card Container
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(MaterialTheme.invexDimens.radiusLg))
          .background(MaterialTheme.invexColors.bgCardPrimary)
          .border(
            width = 1.dp,
            color = MaterialTheme.invexColors.textPrimary,
            shape = RoundedCornerShape(MaterialTheme.invexDimens.radiusLg)
          )
          .padding(20.dp)
          .testTag("admit_card_container")
      ) {
        Column(
          modifier = Modifier.fillMaxWidth()
        ) {
          // Header Row: Admit Card label + Profile Avatar + Columns 1 & 2
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
          ) {
            // Left block: "Admit Card" + Avatar
            Column(
              horizontalAlignment = Alignment.Start,
              modifier = Modifier.width(80.dp)
            ) {
              Text(
                text = "Admit\nCard",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.invexColors.textPrimary,
                lineHeight = 22.sp
              )

              Spacer(modifier = Modifier.height(14.dp))

              // Avatar Illustration matching Figma
              StudentAvatarGraphic(
                modifier = Modifier
                  .size(68.dp)
                  .clip(CircleShape)
                  .background(Color(0xFFF0F0F0))
              )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Middle: Fields Column 1
            Column(
              modifier = Modifier.weight(1f),
              verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
              ProfileFieldItem(label = "Name", value = "John Doe")
              ProfileFieldItem(label = "Course", value = "Lorem Ipsum")
              ProfileFieldItem(label = "Semester", value = "Lorem Ipsum")
            }

            Spacer(modifier = Modifier.width(10.dp))

            // Right: Fields Column 2
            Column(
              modifier = Modifier.weight(1.1f),
              verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
              ProfileFieldItem(label = "Country", value = "Lorem Ipsum")
              ProfileFieldItem(label = "Specialization", value = "Lorem Ipsum")
              ProfileFieldItem(label = "Batch", value = "Lorem Ipsum")
            }
          }

          Spacer(modifier = Modifier.height(16.dp))
          HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.invexColors.borderSubtle
          )
          Spacer(modifier = Modifier.height(14.dp))

          // Exam Schedule Table
          scheduleEntries.forEach { entry ->
            ExamScheduleRow(entry = entry)
            Spacer(modifier = Modifier.height(12.dp))
          }

          Spacer(modifier = Modifier.height(8.dp))

          // Verification Footer: QR Code Box + Signature Box
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            // QR Code Box
            Box(
              modifier = Modifier
                .size(76.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFFAFAFA))
                .padding(8.dp),
              contentAlignment = Alignment.Center
            ) {
              AdmitCardQrGraphic(modifier = Modifier.fillMaxSize())
            }

            // Signature Box
            Box(
              modifier = Modifier
                .weight(1f)
                .height(76.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFFAFAFA))
                .padding(horizontal = 14.dp),
              contentAlignment = Alignment.Center
            ) {
              DigitalSignatureWave(modifier = Modifier.fillMaxSize())
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(28.dp))
    }
  }

  // Verification Dialog
  if (showVerifyDialog) {
    AlertDialog(
      onDismissRequest = { showVerifyDialog = false },
      icon = {
        Icon(
          imageVector = Icons.Default.CheckCircle,
          contentDescription = null,
          tint = Color(0xFF2E7D32),
          modifier = Modifier.size(40.dp)
        )
      },
      title = {
        Text("Official Card Verified", fontWeight = FontWeight.Bold)
      },
      text = {
        Text("Digital signature hash: #INVEX-2026-990-VALID. This admit card is authenticated by the Controller of Examinations.")
      },
      confirmButton = {
        TextButton(onClick = { showVerifyDialog = false }) {
          Text("Done", fontWeight = FontWeight.Bold, color = MaterialTheme.invexColors.textPrimary)
        }
      }
    )
  }
}

@Composable
fun ProfileFieldItem(label: String, value: String) {
  Column {
    Text(
      text = label,
      fontSize = 11.sp,
      color = MaterialTheme.invexColors.textSecondary,
      fontWeight = FontWeight.Medium
    )
    Text(
      text = value,
      fontSize = 13.sp,
      color = MaterialTheme.invexColors.textPrimary,
      fontWeight = FontWeight.Bold
    )
  }
}

@Composable
fun ExamScheduleRow(entry: ExamScheduleEntry) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Column(modifier = Modifier.weight(1.3f)) {
      Text(
        text = "Course",
        fontSize = 11.sp,
        color = MaterialTheme.invexColors.textSecondary,
        fontWeight = FontWeight.Medium
      )
      Text(
        text = entry.course,
        fontSize = 13.sp,
        color = MaterialTheme.invexColors.textPrimary,
        fontWeight = FontWeight.Bold
      )
    }

    Column(modifier = Modifier.weight(1.1f)) {
      Text(
        text = "Date",
        fontSize = 11.sp,
        color = MaterialTheme.invexColors.textSecondary,
        fontWeight = FontWeight.Medium
      )
      Text(
        text = entry.date,
        fontSize = 13.sp,
        color = MaterialTheme.invexColors.textPrimary,
        fontWeight = FontWeight.Bold
      )
    }

    Column(modifier = Modifier.weight(0.6f), horizontalAlignment = Alignment.End) {
      Text(
        text = "Shift",
        fontSize = 11.sp,
        color = MaterialTheme.invexColors.textSecondary,
        fontWeight = FontWeight.Medium
      )
      Text(
        text = entry.shift,
        fontSize = 13.sp,
        color = MaterialTheme.invexColors.textPrimary,
        fontWeight = FontWeight.Bold
      )
    }
  }
}

/**
 * Avatar graphic matching the Figma face illustration
 */
@Composable
fun StudentAvatarGraphic(modifier: Modifier = Modifier) {
  Canvas(modifier = modifier) {
    val w = size.width
    val h = size.height

    // Hair outline / shape
    val hairPath = Path().apply {
      moveTo(w * 0.15f, h * 0.5f)
      cubicTo(w * 0.1f, h * 0.15f, w * 0.9f, h * 0.15f, w * 0.85f, h * 0.5f)
      cubicTo(w * 0.92f, h * 0.85f, w * 0.75f, h * 0.95f, w * 0.7f, h * 0.75f)
      cubicTo(w * 0.6f, h * 0.88f, w * 0.4f, h * 0.88f, w * 0.3f, h * 0.75f)
      cubicTo(w * 0.25f, h * 0.95f, w * 0.08f, h * 0.85f, w * 0.15f, h * 0.5f)
      close()
    }
    drawPath(path = hairPath, color = Color(0xFF1A1A1A))

    // Face inner circle
    drawCircle(
      color = Color(0xFFFFE0BD),
      radius = w * 0.30f,
      center = Offset(w * 0.5f, h * 0.55f)
    )

    // Eyes
    drawCircle(
      color = Color.Black,
      radius = 2.2.dp.toPx(),
      center = Offset(w * 0.42f, h * 0.52f)
    )
    drawCircle(
      color = Color.Black,
      radius = 2.2.dp.toPx(),
      center = Offset(w * 0.58f, h * 0.52f)
    )

    // Smile
    val smilePath = Path().apply {
      moveTo(w * 0.44f, h * 0.65f)
      quadraticBezierTo(w * 0.5f, h * 0.72f, w * 0.56f, h * 0.65f)
    }
    drawPath(
      path = smilePath,
      color = Color.Black,
      style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
    )
  }
}

/**
 * QR Code graphic representation
 */
@Composable
fun AdmitCardQrGraphic(modifier: Modifier = Modifier) {
  Canvas(modifier = modifier) {
    val w = size.width
    val h = size.height
    val strokeWidth = 3.dp.toPx()

    // 3 Corner marker blocks
    val markerSize = w * 0.34f

    // Top-left
    drawRect(
      color = Color.Black,
      topLeft = Offset(w * 0.06f, h * 0.06f),
      size = Size(markerSize, markerSize),
      style = Stroke(width = strokeWidth)
    )
    drawRect(
      color = Color.Black,
      topLeft = Offset(w * 0.14f, h * 0.14f),
      size = Size(markerSize * 0.5f, markerSize * 0.5f)
    )

    // Top-right
    drawRect(
      color = Color.Black,
      topLeft = Offset(w * 0.60f, h * 0.06f),
      size = Size(markerSize, markerSize),
      style = Stroke(width = strokeWidth)
    )
    drawRect(
      color = Color.Black,
      topLeft = Offset(w * 0.68f, h * 0.14f),
      size = Size(markerSize * 0.5f, markerSize * 0.5f)
    )

    // Bottom-left
    drawRect(
      color = Color.Black,
      topLeft = Offset(w * 0.06f, h * 0.60f),
      size = Size(markerSize, markerSize),
      style = Stroke(width = strokeWidth)
    )
    drawRect(
      color = Color.Black,
      topLeft = Offset(w * 0.14f, h * 0.68f),
      size = Size(markerSize * 0.5f, markerSize * 0.5f)
    )

    // Data dots in bottom-right
    val dotSize = 3.5.dp.toPx()
    drawRect(color = Color.Black, topLeft = Offset(w * 0.62f, h * 0.62f), size = Size(dotSize, dotSize))
    drawRect(color = Color.Black, topLeft = Offset(w * 0.78f, h * 0.62f), size = Size(dotSize, dotSize))
    drawRect(color = Color.Black, topLeft = Offset(w * 0.62f, h * 0.78f), size = Size(dotSize, dotSize))
    drawRect(color = Color.Black, topLeft = Offset(w * 0.78f, h * 0.78f), size = Size(dotSize, dotSize))
  }
}

/**
 * Signature wave curve matching Figma
 */
@Composable
fun DigitalSignatureWave(modifier: Modifier = Modifier) {
  Canvas(modifier = modifier) {
    val w = size.width
    val h = size.height

    val path = Path().apply {
      moveTo(w * 0.08f, h * 0.65f)
      cubicTo(
        w * 0.25f, h * 0.35f,
        w * 0.40f, h * 0.85f,
        w * 0.60f, h * 0.85f
      )
      cubicTo(
        w * 0.75f, h * 0.85f,
        w * 0.88f, h * 0.70f,
        w * 0.95f, h * 0.65f
      )
    }

    drawPath(
      path = path,
      color = Color.Black,
      style = Stroke(width = 2.8.dp.toPx(), cap = StrokeCap.Round)
    )
  }
}

@Preview(
  name = "AdmitCardScreen Light Mode",
  showBackground = true,
  device = "spec:width=411dp,height=891dp,dpi=420"
)
@Composable
fun AdmitCardScreenPreview() {
  InvExTheme(darkTheme = false) {
    AdmitCardScreen()
  }
}
