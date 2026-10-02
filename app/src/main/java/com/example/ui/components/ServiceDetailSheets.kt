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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.Devices
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material.icons.outlined.FileUpload
import androidx.compose.material.icons.outlined.QrCode
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material.icons.outlined.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.invexColors
import com.example.ui.theme.invexDimens
import com.example.ui.theme.invexTypography

/**
 * Admit Card Sheet displaying student credentials, session info,
 * QR verification slot, and dual action buttons (Download / Verify).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdmitCardSheet(
  onDismiss: () -> Unit,
  onDownload: () -> Unit = {},
  onVerify: () -> Unit = {}
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = MaterialTheme.invexColors.bgScreen,
    dragHandle = null
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp)
        .testTag("sheet_admit_card")
    ) {
      // SubPageHeader
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Admit Card",
            style = MaterialTheme.invexTypography.headingLg,
            color = MaterialTheme.invexColors.textPrimary
          )
          Text(
            text = "Academic Session 2026-27 (Fall Semester)",
            style = MaterialTheme.invexTypography.caption,
            color = MaterialTheme.invexColors.textSecondary
          )
        }
        IconButton(onClick = onDismiss) {
          Icon(Icons.Default.Close, contentDescription = "Close Admit Card")
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // DataCard
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(MaterialTheme.invexDimens.radiusLg))
          .background(MaterialTheme.invexColors.bgCardPrimary)
          .border(1.dp, MaterialTheme.invexColors.borderSubtle, RoundedCornerShape(MaterialTheme.invexDimens.radiusLg))
          .padding(18.dp)
      ) {
        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
          // Header: Avatar + Meta
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
          ) {
            Box(
              modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(MaterialTheme.invexColors.bgCardSecondary),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = "AJ",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.invexColors.textPrimary
              )
            }

            Column {
              Text(
                text = "Alex Johnson",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.invexColors.textPrimary
              )
              Text(
                text = "Roll No: CS2026-890",
                fontSize = 13.sp,
                color = MaterialTheme.invexColors.textSecondary
              )
              Text(
                text = "B.Tech Computer Science & Eng.",
                fontSize = 12.sp,
                color = MaterialTheme.invexColors.textSecondary
              )
            }
          }

          // Table Grid
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(8.dp))
              .background(MaterialTheme.invexColors.bgCardSecondary.copy(alpha = 0.5f))
              .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            DataRow("Course", "Object Oriented Programming (OOPS)")
            DataRow("Date", "Oct 16, 2026")
            DataRow("Shift & Time", "Morning (09:00 AM - 12:00 PM)")
            DataRow("Hall / Desk", "Academic Block C, Room 302")
          }

          // QR Code Verification Box
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "Digital Verification",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.invexColors.textPrimary
              )
              Text(
                text = "Scan at examination gate",
                fontSize = 11.sp,
                color = MaterialTheme.invexColors.textSecondary
              )
            }

            Box(
              modifier = Modifier
                .size(68.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, MaterialTheme.invexColors.borderSubtle, RoundedCornerShape(8.dp))
                .background(MaterialTheme.invexColors.bgCardPrimary),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Outlined.QrCode,
                contentDescription = "Admit Card QR",
                tint = MaterialTheme.invexColors.textPrimary,
                modifier = Modifier.size(52.dp)
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(18.dp))

      // Dual Action Bar: Download & Verify
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        Button(
          onClick = onDownload,
          modifier = Modifier
            .weight(1f)
            .height(48.dp)
            .testTag("btn_admit_download"),
          colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.invexColors.bgDark,
            contentColor = MaterialTheme.invexColors.textInverse
          ),
          shape = RoundedCornerShape(MaterialTheme.invexDimens.radiusMd)
        ) {
          Text("Download PDF", fontWeight = FontWeight.Bold)
        }

        OutlinedButton(
          onClick = onVerify,
          modifier = Modifier
            .weight(1f)
            .height(48.dp)
            .testTag("btn_admit_verify"),
          shape = RoundedCornerShape(MaterialTheme.invexDimens.radiusMd)
        ) {
          Text("Verify Card", fontWeight = FontWeight.Bold, color = MaterialTheme.invexColors.textPrimary)
        }
      }
    }
  }
}

@Composable
private fun DataRow(label: String, value: String) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Text(
      text = label,
      fontSize = 12.sp,
      fontWeight = FontWeight.Medium,
      color = MaterialTheme.invexColors.textSecondary
    )
    Text(
      text = value,
      fontSize = 12.sp,
      fontWeight = FontWeight.Bold,
      color = MaterialTheme.invexColors.textPrimary
    )
  }
}

/**
 * Do's & Don'ts Sheet displaying categorized exam hall instructions.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DosAndDontsSheet(
  onDismiss: () -> Unit
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = MaterialTheme.invexColors.bgScreen,
    dragHandle = null
  ) {
    LazyColumn(
      modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp)
        .testTag("sheet_dos_donts")
    ) {
      item {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Examination Rules",
            style = MaterialTheme.invexTypography.headingLg,
            color = MaterialTheme.invexColors.textPrimary
          )
          IconButton(onClick = onDismiss) {
            Icon(Icons.Default.Close, contentDescription = "Close Rules")
          }
        }
        Spacer(modifier = Modifier.height(14.dp))
      }

      // Allowed Items: CARRY
      item {
        Text(
          text = "CARRY (Allowed)",
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.invexColors.textPrimary,
          modifier = Modifier.padding(bottom = 10.dp)
        )
      }

      item {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          InstructionRuleTile(
            title = "Digital Admit Card",
            icon = Icons.Outlined.Article,
            isAllowed = true,
            modifier = Modifier.weight(1f)
          )
          InstructionRuleTile(
            title = "Ball Point Pen",
            icon = Icons.Outlined.Edit,
            isAllowed = true,
            modifier = Modifier.weight(1f)
          )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          InstructionRuleTile(
            title = "Water Bottle",
            icon = Icons.Outlined.WaterDrop,
            isAllowed = true,
            modifier = Modifier.weight(1f)
          )
          InstructionRuleTile(
            title = "Calculator",
            icon = Icons.Outlined.Calculate,
            isAllowed = true,
            modifier = Modifier.weight(1f)
          )
        }
        Spacer(modifier = Modifier.height(20.dp))
      }

      // Prohibited Items: AVOID
      item {
        Text(
          text = "AVOID (Strictly Prohibited)",
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.invexColors.dangerRed,
          modifier = Modifier.padding(bottom = 10.dp)
        )
      }

      item {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          InstructionRuleTile(
            title = "Smart Devices",
            icon = Icons.Outlined.Devices,
            isAllowed = false,
            modifier = Modifier.weight(1f)
          )
          InstructionRuleTile(
            title = "Backpacks",
            icon = Icons.Outlined.Work,
            isAllowed = false,
            modifier = Modifier.weight(1f)
          )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          InstructionRuleTile(
            title = "Food / Snacks",
            icon = Icons.Outlined.Fastfood,
            isAllowed = false,
            modifier = Modifier.weight(1f)
          )
          InstructionRuleTile(
            title = "Paper Notes",
            icon = Icons.Outlined.Article,
            isAllowed = false,
            modifier = Modifier.weight(1f)
          )
        }
        Spacer(modifier = Modifier.height(24.dp))
      }
    }
  }
}

@Composable
fun InstructionRuleTile(
  title: String,
  icon: ImageVector,
  isAllowed: Boolean,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .clip(RoundedCornerShape(12.dp))
      .background(MaterialTheme.invexColors.bgCardPrimary)
      .border(1.dp, MaterialTheme.invexColors.borderSubtle, RoundedCornerShape(12.dp))
      .padding(14.dp)
  ) {
    Column {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = icon,
          contentDescription = title,
          tint = if (isAllowed) MaterialTheme.invexColors.textPrimary else MaterialTheme.invexColors.dangerRed,
          modifier = Modifier.size(24.dp)
        )

        Icon(
          imageVector = if (isAllowed) Icons.Default.Check else Icons.Default.Close,
          contentDescription = if (isAllowed) "Allowed" else "Prohibited",
          tint = if (isAllowed) Color(0xFF2E7D32) else MaterialTheme.invexColors.dangerRed,
          modifier = Modifier.size(18.dp)
        )
      }
      Spacer(modifier = Modifier.height(12.dp))
      Text(
        text = title,
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.invexColors.textPrimary
      )
    }
  }
}

/**
 * Results Sheet displaying marks and semester selector.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultsSheet(
  onDismiss: () -> Unit
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = MaterialTheme.invexColors.bgScreen,
    dragHandle = null
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp)
        .testTag("sheet_results")
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Academic Results",
            style = MaterialTheme.invexTypography.headingLg,
            color = MaterialTheme.invexColors.textPrimary
          )
          Text(
            text = "Term Grade Sheet & SGPA",
            style = MaterialTheme.invexTypography.caption,
            color = MaterialTheme.invexColors.textSecondary
          )
        }
        IconButton(onClick = onDismiss) {
          Icon(Icons.Default.Close, contentDescription = "Close Results")
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Semester Indicator Card
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(14.dp))
          .background(MaterialTheme.invexColors.bgCardPrimary)
          .padding(14.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text("Selected Term", fontSize = 12.sp, color = MaterialTheme.invexColors.textSecondary)
            Text("Semester Third (Fall 2026)", fontSize = 15.sp, fontWeight = FontWeight.Bold)
          }
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(MaterialTheme.invexColors.accentLime)
              .padding(horizontal = 12.dp, vertical = 6.dp)
          ) {
            Text("CGPA: 8.84", fontSize = 13.sp, fontWeight = FontWeight.Bold)
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Course grades list
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(16.dp))
          .background(MaterialTheme.invexColors.bgCardPrimary)
          .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        ResultRow("Data Structures & Algorithms", "A+", "10.0")
        ResultRow("Computer Architecture", "A", "9.0")
        ResultRow("Object Oriented Programming", "In Progress", "--")
        ResultRow("Discrete Mathematics", "A+", "10.0")
      }
    }
  }
}

@Composable
private fun ResultRow(course: String, grade: String, points: String) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(course, fontSize = 13.sp, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
      Text(grade, fontSize = 13.sp, fontWeight = FontWeight.Bold)
      Text("($points)", fontSize = 13.sp, color = MaterialTheme.invexColors.textSecondary)
    }
  }
}

/**
 * Medical Leave Form Sheet with form fields, multi-line symptoms input,
 * and file upload control.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicalLeaveSheet(
  onDismiss: () -> Unit,
  onSubmit: (String) -> Unit = {}
) {
  val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
  var details by remember { mutableStateOf("") }
  var fileSelected by remember { mutableStateOf(false) }

  ModalBottomSheet(
    onDismissRequest = onDismiss,
    sheetState = sheetState,
    containerColor = MaterialTheme.invexColors.bgScreen,
    dragHandle = null
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp)
        .testTag("sheet_medical_leave")
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "Medical Leave Request",
            style = MaterialTheme.invexTypography.headingLg,
            color = MaterialTheme.invexColors.textPrimary
          )
          Text(
            text = "Submit medical certificates for exam exemption",
            style = MaterialTheme.invexTypography.caption,
            color = MaterialTheme.invexColors.textSecondary
          )
        }
        IconButton(onClick = onDismiss) {
          Icon(Icons.Default.Close, contentDescription = "Close Leave Form")
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Student Roll (Read-Only)
      Text(
        text = "Student Roll Number",
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.invexColors.textSecondary
      )
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 4.dp, bottom = 12.dp)
          .clip(RoundedCornerShape(8.dp))
          .background(MaterialTheme.invexColors.bgCardSecondary)
          .padding(12.dp)
      ) {
        Text("CS2026-890 — Alex Johnson", fontSize = 14.sp, fontWeight = FontWeight.Medium)
      }

      // Medical Details input
      Text(
        text = "Medical Reason & Details",
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.invexColors.textSecondary
      )
      OutlinedTextField(
        value = details,
        onValueChange = { details = it },
        placeholder = { Text("Enter the Medical Details.") },
        modifier = Modifier
          .fillMaxWidth()
          .height(100.dp)
          .padding(top = 4.dp, bottom = 12.dp)
          .testTag("input_medical_details")
      )

      // File Uploader Control
      Text(
        text = "Upload Document",
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.invexColors.textSecondary
      )
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 4.dp, bottom = 6.dp)
          .clip(RoundedCornerShape(12.dp))
          .background(MaterialTheme.invexColors.bgCardPrimary)
          .border(1.dp, MaterialTheme.invexColors.borderSubtle, RoundedCornerShape(12.dp))
          .clickable { fileSelected = !fileSelected }
          .padding(14.dp),
        contentAlignment = Alignment.Center
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Icon(Icons.Outlined.FileUpload, contentDescription = "Upload Document")
          Text(
            text = if (fileSelected) "medical_certificate_alex.pdf (1.4 MB)" else "Choose Files",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.invexColors.textPrimary
          )
        }
      }
      Text(
        text = "Valid files : JPG, PDF, DOC, TXT under 10 Mb size.",
        fontSize = 11.sp,
        color = MaterialTheme.invexColors.dangerRed,
        modifier = Modifier.padding(bottom = 16.dp)
      )

      // Submit Button
      Button(
        onClick = { onSubmit(details) },
        modifier = Modifier
          .fillMaxWidth()
          .height(48.dp)
          .testTag("btn_submit_medical_leave"),
        colors = ButtonDefaults.buttonColors(
          containerColor = MaterialTheme.invexColors.bgDark,
          contentColor = MaterialTheme.invexColors.textInverse
        ),
        shape = RoundedCornerShape(MaterialTheme.invexDimens.radiusMd)
      ) {
        Text("Submit Medical Application", fontWeight = FontWeight.Bold)
      }
    }
  }
}
