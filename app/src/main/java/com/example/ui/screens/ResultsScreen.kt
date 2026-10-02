package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
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
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.UnfoldMore
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.PanToolAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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

data class SemesterOption(
  val id: String,
  val label: String,
  val isDeclared: Boolean,
  val sgpa: Double? = null
)

data class SubjectGrade(
  val code: String,
  val title: String,
  val credits: Int,
  val grade: String,
  val points: Double
)

/**
 * Screen 3: Results Screen (Service - Results Selection)
 * Implements interactive semester dropdown picker and results breakdown view.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultsScreen(
  onBackClick: () -> Unit = {},
  modifier: Modifier = Modifier
) {
  BackHandler { onBackClick() }

  val coroutineScope = rememberCoroutineScope()
  val snackbarHostState = remember { SnackbarHostState() }
  var showSemesterPicker by remember { mutableStateOf(false) }

  val semesters = remember {
    listOf(
      SemesterOption("sem1", "Semester First", isDeclared = true, sgpa = 8.65),
      SemesterOption("sem2", "Semester Second", isDeclared = true, sgpa = 8.78),
      SemesterOption("sem3", "Semester Third", isDeclared = true, sgpa = 8.92),
      SemesterOption("sem4", "Semester Fourth", isDeclared = false),
      SemesterOption("sem5", "Semester Fifth", isDeclared = false),
      SemesterOption("sem6", "Semester Sixth", isDeclared = false)
    )
  }

  var selectedSemester by remember { mutableStateOf(semesters[2]) } // "Semester Third" default from Figma
  var showResultsDetail by remember { mutableStateOf(false) }

  val thirdSemesterGrades = remember {
    listOf(
      SubjectGrade("CS301", "Object Oriented Programming (Java)", 4, "A+", 10.0),
      SubjectGrade("CS302", "Advanced Data Structures & Algorithms", 4, "A+", 10.0),
      SubjectGrade("CS303", "Computer Architecture & Organization", 3, "A", 9.0),
      SubjectGrade("MA301", "Discrete Mathematics & Graph Theory", 4, "A+", 10.0),
      SubjectGrade("CS304", "Data Communication & Computer Networks", 3, "B+", 8.0)
    )
  }

  Scaffold(
    modifier = modifier
      .fillMaxSize()
      .testTag("results_screen"),
    containerColor = MaterialTheme.invexColors.bgScreen,
    contentWindowInsets = WindowInsets(0, 0, 0, 0),
    snackbarHost = { SnackbarHost(snackbarHostState) }
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
            .testTag("btn_results_back")
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
          text = "Results",
          fontSize = 32.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.SansSerif,
          color = MaterialTheme.invexColors.textPrimary
        )
      }

      Spacer(modifier = Modifier.height(60.dp))

      // Semester Selection Area: Centered layout matching Figma screenshot
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Instructional helper label: Touch icon + "Choose your semester"
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center,
          modifier = Modifier.padding(bottom = 18.dp)
        ) {
          Icon(
            imageVector = Icons.Outlined.PanToolAlt,
            contentDescription = null,
            tint = MaterialTheme.invexColors.textSecondary,
            modifier = Modifier.size(24.dp)
          )

          Spacer(modifier = Modifier.width(10.dp))

          Text(
            text = "Choose your semester",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.invexColors.textSecondary
          )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Dropdown Field: "Semester Third ↕"
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center,
          modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable(
              role = Role.Button,
              onClick = { showSemesterPicker = true }
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .testTag("dropdown_semester_selector")
        ) {
          Text(
            text = selectedSemester.label,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            color = MaterialTheme.invexColors.textPrimary
          )

          Spacer(modifier = Modifier.width(8.dp))

          Icon(
            imageVector = Icons.Default.UnfoldMore,
            contentDescription = "Open semester picker",
            tint = MaterialTheme.invexColors.textPrimary,
            modifier = Modifier.size(28.dp)
          )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Quick View / Toggle results button
        Button(
          onClick = {
            if (selectedSemester.isDeclared) {
              showResultsDetail = !showResultsDetail
            } else {
              coroutineScope.launch {
                snackbarHostState.showSnackbar("Results for ${selectedSemester.label} are not declared yet.")
              }
            }
          },
          colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.invexColors.bgDark,
            contentColor = MaterialTheme.invexColors.textInverse
          ),
          shape = RoundedCornerShape(MaterialTheme.invexDimens.radiusMd),
          modifier = Modifier
            .height(48.dp)
            .testTag("btn_view_marksheet")
        ) {
          Text(
            text = if (showResultsDetail) "Hide Marks Breakdown" else "View Grade Sheet",
            fontWeight = FontWeight.Bold
          )
        }
      }

      Spacer(modifier = Modifier.height(30.dp))

      // Dynamic Results Loaded State
      AnimatedVisibility(
        visible = showResultsDetail && selectedSemester.isDeclared,
        enter = fadeIn() + slideInVertically()
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .testTag("results_details_card")
        ) {
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(MaterialTheme.invexDimens.radiusLg),
            colors = CardDefaults.cardColors(
              containerColor = MaterialTheme.invexColors.bgCardPrimary
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
          ) {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
            ) {
              // SGPA / CGPA Summary Banner
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .clip(RoundedCornerShape(12.dp))
                  .background(MaterialTheme.invexColors.bgCardSecondary)
                  .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Column {
                  Text(
                    text = selectedSemester.label,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.invexColors.textPrimary
                  )
                  Text(
                    text = "Status: Passed with Distinction",
                    fontSize = 12.sp,
                    color = MaterialTheme.invexColors.textSecondary
                  )
                }

                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.invexColors.accentLime)
                    .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                  Text(
                    text = "SGPA: ${selectedSemester.sgpa ?: "--"}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.invexColors.textPrimary
                  )
                }
              }

              Spacer(modifier = Modifier.height(16.dp))

              Text(
                text = "Course Marks & Grades",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.invexColors.textPrimary
              )

              Spacer(modifier = Modifier.height(10.dp))

              // Subject Rows
              thirdSemesterGrades.forEach { subject ->
                Row(
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Column(modifier = Modifier.weight(1f)) {
                    Text(
                      text = subject.title,
                      fontSize = 13.sp,
                      fontWeight = FontWeight.SemiBold,
                      color = MaterialTheme.invexColors.textPrimary
                    )
                    Text(
                      text = "${subject.code} • ${subject.credits} Credits",
                      fontSize = 11.sp,
                      color = MaterialTheme.invexColors.textSecondary
                    )
                  }

                  Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                  ) {
                    Text(
                      text = subject.grade,
                      fontSize = 14.sp,
                      fontWeight = FontWeight.Bold,
                      color = MaterialTheme.invexColors.textPrimary
                    )
                    Text(
                      text = "(${subject.points})",
                      fontSize = 12.sp,
                      color = MaterialTheme.invexColors.textSecondary
                    )
                  }
                }
                HorizontalDivider(color = MaterialTheme.invexColors.borderSubtle.copy(alpha = 0.5f))
              }

              Spacer(modifier = Modifier.height(16.dp))

              // Download Marksheet CTA
              Button(
                onClick = {
                  coroutineScope.launch {
                    snackbarHostState.showSnackbar("Downloading official marksheet for ${selectedSemester.label}...")
                  }
                },
                modifier = Modifier
                  .fillMaxWidth()
                  .height(48.dp)
                  .testTag("btn_download_marksheet"),
                colors = ButtonDefaults.buttonColors(
                  containerColor = MaterialTheme.invexColors.bgDark,
                  contentColor = MaterialTheme.invexColors.textInverse
                ),
                shape = RoundedCornerShape(MaterialTheme.invexDimens.radiusMd)
              ) {
                Icon(Icons.Outlined.Download, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Download Official Marksheet", fontWeight = FontWeight.Bold)
              }
            }
          }
        }
      }

      // Empty / Result Pending State indicator
      if (!selectedSemester.isDeclared) {
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .testTag("pending_result_card"),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.invexColors.bgCardPrimary
          )
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Info,
              contentDescription = null,
              tint = MaterialTheme.invexColors.warningAmber,
              modifier = Modifier.size(28.dp)
            )
            Column {
              Text(
                text = "Results for ${selectedSemester.label} are not declared yet.",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.invexColors.textPrimary
              )
              Text(
                text = "Please check back after official announcement.",
                fontSize = 12.sp,
                color = MaterialTheme.invexColors.textSecondary
              )
            }
          }
        }
      }

      Spacer(
        modifier = Modifier.height(
          WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 24.dp
        )
      )
    }
  }

  // Semester Selection Modal Bottom Sheet
  if (showSemesterPicker) {
    val sheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
      onDismissRequest = { showSemesterPicker = false },
      sheetState = sheetState,
      containerColor = MaterialTheme.invexColors.bgScreen
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(20.dp)
          .testTag("sheet_semester_picker")
      ) {
        Text(
          text = "Select Semester",
          style = MaterialTheme.invexTypography.headingLg,
          color = MaterialTheme.invexColors.textPrimary,
          modifier = Modifier.padding(bottom = 16.dp)
        )

        semesters.forEach { semester ->
          val isSelected = semester.id == selectedSemester.id
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(12.dp))
              .background(if (isSelected) MaterialTheme.invexColors.bgCardSecondary else MaterialTheme.invexColors.bgCardPrimary)
              .clickable {
                selectedSemester = semester
                showResultsDetail = semester.isDeclared
                showSemesterPicker = false
              }
              .padding(horizontal = 16.dp, vertical = 14.dp)
              .testTag("option_${semester.id}"),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = semester.label,
                fontSize = 16.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = MaterialTheme.invexColors.textPrimary
              )
              Text(
                text = if (semester.isDeclared) "Results Declared (SGPA: ${semester.sgpa})" else "Evaluation in progress",
                fontSize = 12.sp,
                color = if (semester.isDeclared) Color(0xFF2E7D32) else MaterialTheme.invexColors.textSecondary
              )
            }

            if (isSelected) {
              Box(
                modifier = Modifier
                  .size(24.dp)
                  .clip(CircleShape)
                  .background(MaterialTheme.invexColors.bgDark),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.Check,
                  contentDescription = "Selected",
                  tint = MaterialTheme.invexColors.textInverse,
                  modifier = Modifier.size(16.dp)
                )
              }
            }
          }
          Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))
      }
    }
  }
}

@Preview(
  name = "ResultsScreen Light Mode",
  showBackground = true,
  device = "spec:width=411dp,height=891dp,dpi=420"
)
@Composable
fun ResultsScreenPreview() {
  InvExTheme(darkTheme = false) {
    ResultsScreen()
  }
}
