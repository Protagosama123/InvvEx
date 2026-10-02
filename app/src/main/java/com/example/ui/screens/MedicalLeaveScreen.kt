package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.UnfoldMore
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.InvExTheme
import com.example.ui.theme.invexColors
import com.example.ui.theme.invexDimens
import com.example.ui.theme.invexTypography
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class MedicalLeaveStep {
  STEP_1_VERIFY,
  STEP_2_FORM
}

data class AttachedFile(
  val name: String,
  val sizeMb: Double,
  val extension: String
)

/**
 * Screen 4: Medical Leave Screen
 * Strictly adheres to the 2-step application workflow:
 * - Step 1: Read-Only Student Data Verification
 * - Step 2: Medical Form & File Upload
 */
@Composable
fun MedicalLeaveScreen(
  onBackClick: () -> Unit = {},
  modifier: Modifier = Modifier
) {
  var currentStep by remember { mutableStateOf(MedicalLeaveStep.STEP_1_VERIFY) }

  // Intercept back button to return to Step 1 from Step 2, or exit to home from Step 1
  BackHandler {
    if (currentStep == MedicalLeaveStep.STEP_2_FORM) {
      currentStep = MedicalLeaveStep.STEP_1_VERIFY
    } else {
      onBackClick()
    }
  }

  AnimatedContent(
    targetState = currentStep,
    transitionSpec = { fadeIn() togetherWith fadeOut() },
    label = "MedicalLeaveStepTransition"
  ) { step ->
    when (step) {
      MedicalLeaveStep.STEP_1_VERIFY -> {
        MedicalLeaveStep1Screen(
          onBackClick = onBackClick,
          onCancelClick = onBackClick,
          onProceedClick = { currentStep = MedicalLeaveStep.STEP_2_FORM },
          modifier = modifier
        )
      }
      MedicalLeaveStep.STEP_2_FORM -> {
        MedicalLeaveStep2Screen(
          onBackClick = { currentStep = MedicalLeaveStep.STEP_1_VERIFY },
          onCancelClick = { currentStep = MedicalLeaveStep.STEP_1_VERIFY },
          onSubmissionSuccess = onBackClick,
          modifier = modifier
        )
      }
    }
  }
}

/**
 * Step 1: Student Data Verification (Read-Only fields matching Figma Image 4)
 */
@Composable
fun MedicalLeaveStep1Screen(
  onBackClick: () -> Unit,
  onCancelClick: () -> Unit,
  onProceedClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Scaffold(
    modifier = modifier
      .fillMaxSize()
      .testTag("medical_leave_step_1"),
    containerColor = MaterialTheme.invexColors.bgScreen,
    contentWindowInsets = WindowInsets(0, 0, 0, 0),
    bottomBar = {
      // Bottom Sticky Action Bar: Cancel & Proceed
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
          .testTag("step1_action_bar"),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        // Cancel Button (Light cream-tint container)
        Box(
          modifier = Modifier
            .weight(1f)
            .height(54.dp)
            .clip(RoundedCornerShape(MaterialTheme.invexDimens.radiusMd))
            .background(Color(0xFFF2ECE9))
            .border(1.dp, MaterialTheme.invexColors.borderSubtle, RoundedCornerShape(MaterialTheme.invexDimens.radiusMd))
            .clickable(
              role = Role.Button,
              onClick = onCancelClick
            )
            .testTag("btn_step1_cancel"),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "Cancel",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.invexColors.textPrimary
          )
        }

        // Proceed Button (Solid Black container)
        Button(
          onClick = onProceedClick,
          modifier = Modifier
            .weight(1f)
            .height(54.dp)
            .testTag("btn_step1_proceed"),
          colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.invexColors.bgDark,
            contentColor = MaterialTheme.invexColors.textInverse
          ),
          shape = RoundedCornerShape(MaterialTheme.invexDimens.radiusMd)
        ) {
          Text(
            text = "Proceed",
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

      // Header Section
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
      ) {
        IconButton(
          onClick = onBackClick,
          modifier = Modifier
            .size(40.dp)
            .testTag("btn_medical_step1_back")
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
          text = "Medical Leave",
          fontSize = 32.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.SansSerif,
          color = MaterialTheme.invexColors.textPrimary
        )
      }

      Spacer(modifier = Modifier.height(20.dp))

      // Read-Only Student Info Fields matching Figma Frame
      ReadOnlyFormField(
        label = "Application Date",
        value = "29/09/26 19.24.24",
        testTag = "field_app_date"
      )

      ReadOnlyFormField(
        label = "Student Name",
        value = "John Doe",
        testTag = "field_student_name"
      )

      ReadOnlyFormField(
        label = "Enrollment Number",
        value = "0000000000",
        testTag = "field_enrollment_number"
      )

      ReadOnlyFormField(
        label = "Degree",
        value = "Lorem Ipsum Dorem",
        testTag = "field_degree"
      )

      ReadOnlyFormField(
        label = "Branch",
        value = "Lorem",
        testTag = "field_branch"
      )

      ReadOnlyFormField(
        label = "Semester",
        value = "3rd",
        testTag = "field_semester"
      )

      ReadOnlyFormField(
        label = "Mobile Number",
        value = "XXXXXXXXXX",
        testTag = "field_mobile_number"
      )

      ReadOnlyFormField(
        label = "Email Id",
        value = "loremipsum@gmail.com",
        testTag = "field_email_id"
      )

      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}

/**
 * Reusable Read-only Form Display Field (matching Figma pill styling)
 */
@Composable
fun ReadOnlyFormField(
  label: String,
  value: String,
  testTag: String
) {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(bottom = 14.dp)
      .testTag(testTag)
  ) {
    Text(
      text = label,
      fontSize = 15.sp,
      fontWeight = FontWeight.Bold,
      fontFamily = FontFamily.SansSerif,
      color = MaterialTheme.invexColors.textPrimary,
      modifier = Modifier.padding(bottom = 6.dp)
    )

    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(52.dp)
        .clip(RoundedCornerShape(14.dp))
        .background(MaterialTheme.invexColors.bgCardSecondary)
        .padding(horizontal = 16.dp),
      contentAlignment = Alignment.CenterStart
    ) {
      Text(
        text = value,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.SansSerif,
        color = MaterialTheme.invexColors.textPrimary
      )
    }
  }
}

/**
 * Step 2: Medical Form & File Upload (matching Figma Image 5)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicalLeaveStep2Screen(
  onBackClick: () -> Unit,
  onCancelClick: () -> Unit,
  onSubmissionSuccess: () -> Unit,
  modifier: Modifier = Modifier
) {
  val coroutineScope = rememberCoroutineScope()
  val snackbarHostState = remember { SnackbarHostState() }

  // Condition options
  val conditionOptions = listOf(
    "Fever",
    "Injury / Fracture",
    "Viral Infection",
    "Hospitalization / Surgery",
    "Severe Migraine",
    "Other Medical Emergency"
  )

  var selectedCondition by remember { mutableStateOf("Fever") }
  var showConditionPicker by remember { mutableStateOf(false) }
  var medicalDetails by remember { mutableStateOf("") }
  var attachedFile by remember { mutableStateOf<AttachedFile?>(null) }
  var isSubmitting by remember { mutableStateOf(false) }
  var showSuccessDialog by remember { mutableStateOf(false) }

  Scaffold(
    modifier = modifier
      .fillMaxSize()
      .testTag("medical_leave_step_2"),
    containerColor = MaterialTheme.invexColors.bgScreen,
    contentWindowInsets = WindowInsets(0, 0, 0, 0),
    snackbarHost = { SnackbarHost(snackbarHostState) },
    bottomBar = {
      // Bottom Sticky Action Bar: Cancel & Submit
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
          .testTag("step2_action_bar"),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
      ) {
        // Cancel Button (Returns to Step 1)
        Box(
          modifier = Modifier
            .weight(1f)
            .height(54.dp)
            .clip(RoundedCornerShape(MaterialTheme.invexDimens.radiusMd))
            .background(Color(0xFFF2ECE9))
            .border(1.dp, MaterialTheme.invexColors.borderSubtle, RoundedCornerShape(MaterialTheme.invexDimens.radiusMd))
            .clickable(
              role = Role.Button,
              enabled = !isSubmitting,
              onClick = onCancelClick
            )
            .testTag("btn_step2_cancel"),
          contentAlignment = Alignment.Center
        ) {
          Text(
            text = "Cancel",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.invexColors.textPrimary
          )
        }

        // Submit Button (Triggers validation & submission)
        Button(
          onClick = {
            // Validation
            if (medicalDetails.isBlank()) {
              coroutineScope.launch {
                snackbarHostState.showSnackbar("Please enter medical details before submitting.")
              }
              return@Button
            }

            // Begin Submission Flow
            isSubmitting = true
            coroutineScope.launch {
              delay(1200) // Simulating network API call
              isSubmitting = false
              showSuccessDialog = true
            }
          },
          enabled = !isSubmitting,
          modifier = Modifier
            .weight(1f)
            .height(54.dp)
            .testTag("btn_step2_submit"),
          colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.invexColors.bgDark,
            contentColor = MaterialTheme.invexColors.textInverse
          ),
          shape = RoundedCornerShape(MaterialTheme.invexDimens.radiusMd)
        ) {
          if (isSubmitting) {
            CircularProgressIndicator(
              modifier = Modifier.size(22.dp),
              color = MaterialTheme.invexColors.textInverse,
              strokeWidth = 2.5.dp
            )
          } else {
            Text(
              text = "Submit",
              fontSize = 17.sp,
              fontWeight = FontWeight.Bold
            )
          }
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

      // Header Section
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
      ) {
        IconButton(
          onClick = onBackClick,
          modifier = Modifier
            .size(40.dp)
            .testTag("btn_medical_step2_back")
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
          text = "Medical Leave",
          fontSize = 32.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.SansSerif,
          color = MaterialTheme.invexColors.textPrimary
        )
      }

      Spacer(modifier = Modifier.height(24.dp))

      // 1. Medical Condition Label & Dropdown
      Text(
        text = "Medical Condition",
        fontSize = 16.sp,
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
          .background(MaterialTheme.invexColors.bgCardSecondary)
          .clickable(
            role = Role.Button,
            onClick = { showConditionPicker = true }
          )
          .padding(horizontal = 16.dp)
          .testTag("dropdown_medical_condition"),
        contentAlignment = Alignment.CenterStart
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = selectedCondition,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            color = MaterialTheme.invexColors.textPrimary
          )

          Icon(
            imageVector = Icons.Default.UnfoldMore,
            contentDescription = "Select condition",
            tint = MaterialTheme.invexColors.textPrimary,
            modifier = Modifier.size(24.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // 2. Medical Details Label & Textarea
      Text(
        text = "Medical Details",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.SansSerif,
        color = MaterialTheme.invexColors.textPrimary,
        modifier = Modifier.padding(bottom = 8.dp)
      )

      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(180.dp)
          .clip(RoundedCornerShape(16.dp))
          .background(MaterialTheme.invexColors.bgCardSecondary)
          .padding(4.dp)
          .testTag("textarea_medical_details")
      ) {
        OutlinedTextField(
          value = medicalDetails,
          onValueChange = { medicalDetails = it },
          placeholder = {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
              Text(
                text = "Enter the Medical Details.",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                color = MaterialTheme.invexColors.textPrimary.copy(alpha = 0.85f),
                textAlign = TextAlign.Center
              )
            }
          },
          textStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily.SansSerif,
            color = MaterialTheme.invexColors.textPrimary
          ),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = MaterialTheme.invexColors.textPrimary
          ),
          modifier = Modifier.fillMaxSize()
        )
      }

      Spacer(modifier = Modifier.height(24.dp))

      // 3. Upload Document Section
      Text(
        text = "Upload Document",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.SansSerif,
        color = MaterialTheme.invexColors.textPrimary,
        modifier = Modifier.padding(bottom = 12.dp)
      )

      // "Choose Files" Button (Black filled pill button)
      Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
      ) {
        Button(
          onClick = {
            // Simulate file attachment
            attachedFile = AttachedFile(
              name = "Medical_Prescription_${System.currentTimeMillis() % 1000}.pdf",
              sizeMb = 1.45,
              extension = "PDF"
            )
            coroutineScope.launch {
              snackbarHostState.showSnackbar("File attached: ${attachedFile?.name}")
            }
          },
          colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.invexColors.bgDark,
            contentColor = MaterialTheme.invexColors.textInverse
          ),
          shape = CircleShape,
          modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(48.dp)
            .testTag("btn_choose_files")
        ) {
          Text(
            text = "Choose Files",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 14.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Attached File Badge (if selected)
      attachedFile?.let { file ->
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.invexColors.bgCardPrimary)
            .border(1.dp, MaterialTheme.invexColors.borderSubtle, RoundedCornerShape(12.dp))
            .padding(horizontal = 14.dp, vertical = 10.dp)
            .testTag("attached_file_badge"),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
          ) {
            Icon(
              imageVector = Icons.Default.Description,
              contentDescription = null,
              tint = MaterialTheme.invexColors.textPrimary,
              modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
              Text(
                text = file.name,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.invexColors.textPrimary
              )
              Text(
                text = "${file.sizeMb} MB • Validated",
                fontSize = 11.sp,
                color = Color(0xFF2E7D32)
              )
            }
          }

          IconButton(
            onClick = { attachedFile = null },
            modifier = Modifier.size(32.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Close,
              contentDescription = "Remove file",
              tint = MaterialTheme.invexColors.textSecondary,
              modifier = Modifier.size(18.dp)
            )
          }
        }
        Spacer(modifier = Modifier.height(10.dp))
      }

      // Validation / Helper Text (Red accent #D93025)
      Text(
        text = "Valid files : JPG, PDF, DOC, TXT under 10 Mb size.",
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.invexColors.dangerRed,
        textAlign = TextAlign.Center,
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 24.dp)
      )
    }
  }

  // Condition Picker Bottom Sheet
  if (showConditionPicker) {
    val sheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
      onDismissRequest = { showConditionPicker = false },
      sheetState = sheetState,
      containerColor = MaterialTheme.invexColors.bgScreen
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp)
          .testTag("sheet_condition_picker")
      ) {
        Text(
          text = "Select Medical Condition",
          style = MaterialTheme.invexTypography.headingLg,
          color = MaterialTheme.invexColors.textPrimary,
          modifier = Modifier.padding(bottom = 16.dp)
        )

        conditionOptions.forEach { condition ->
          val isSelected = condition == selectedCondition
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(12.dp))
              .background(if (isSelected) MaterialTheme.invexColors.bgCardSecondary else MaterialTheme.invexColors.bgCardPrimary)
              .clickable {
                selectedCondition = condition
                showConditionPicker = false
              }
              .padding(horizontal = 16.dp, vertical = 14.dp)
              .testTag("condition_option_$condition"),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = condition,
              fontSize = 16.sp,
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
              color = MaterialTheme.invexColors.textPrimary
            )

            if (isSelected) {
              Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = MaterialTheme.invexColors.textPrimary
              )
            }
          }
          Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))
      }
    }
  }

  // Submission Success Confirmation Dialog
  if (showSuccessDialog) {
    AlertDialog(
      onDismissRequest = {
        showSuccessDialog = false
        onSubmissionSuccess()
      },
      icon = {
        Icon(
          imageVector = Icons.Default.CheckCircle,
          contentDescription = null,
          tint = Color(0xFF2E7D32),
          modifier = Modifier.size(44.dp)
        )
      },
      title = {
        Text("Application Submitted", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
      },
      text = {
        Text(
          "Your medical leave request (Ref: #ML-2026-8890) has been submitted to the academic dean. You will receive an official status email once reviewed.",
          textAlign = TextAlign.Center,
          lineHeight = 20.sp
        )
      },
      confirmButton = {
        TextButton(
          onClick = {
            showSuccessDialog = false
            onSubmissionSuccess()
          }
        ) {
          Text("Return to Dashboard", fontWeight = FontWeight.Bold, color = MaterialTheme.invexColors.textPrimary)
        }
      }
    )
  }
}

@Preview(
  name = "MedicalLeave - Step 1 Verification",
  showBackground = true,
  device = "spec:width=411dp,height=891dp,dpi=420"
)
@Composable
fun MedicalLeaveStep1Preview() {
  InvExTheme(darkTheme = false) {
    MedicalLeaveStep1Screen(
      onBackClick = {},
      onCancelClick = {},
      onProceedClick = {}
    )
  }
}

@Preview(
  name = "MedicalLeave - Step 2 Form",
  showBackground = true,
  device = "spec:width=411dp,height=891dp,dpi=420"
)
@Composable
fun MedicalLeaveStep2Preview() {
  InvExTheme(darkTheme = false) {
    MedicalLeaveStep2Screen(
      onBackClick = {},
      onCancelClick = {},
      onSubmissionSuccess = {}
    )
  }
}
