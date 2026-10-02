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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.outlined.Apartment
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
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

enum class ExamFilterTab(val label: String) {
  UPCOMING("Upcoming"),
  COMING("Coming")
}

data class ExamCardItem(
  val id: String,
  val title: String,
  val date: String,
  val time: String,
  val location: String,
  val icon: ImageVector? = null,
  val iconGlyph: String? = null,
  val isCompleted: Boolean = false
)

/**
 * Screen: Tab Bar - Exam List View
 * Displays Upcoming and Coming exams with gradient banners, schedule tags, and seating CTA.
 */
@Composable
fun ExamsScreen(
  onNavigateToSeating: (String) -> Unit = {},
  onNavigateToTab: (NavigationTab) -> Unit = {},
  onBackClick: () -> Unit = {},
  modifier: Modifier = Modifier
) {
  BackHandler { onBackClick() }

  val snackbarHostState = remember { SnackbarHostState() }
  var selectedTab by remember { mutableStateOf(ExamFilterTab.UPCOMING) }

  val upcomingExams = remember {
    listOf(
      ExamCardItem(
        id = "oops",
        title = "Object Oriented Programming In Java",
        date = "21 Sep",
        time = "11 AM\n01 PM",
        location = "Examination\nHall A",
        iconGlyph = ">_"
      ),
      ExamCardItem(
        id = "ads",
        title = "Advance Data Structure",
        date = "21 Sep",
        time = "11 AM\n01 PM",
        location = "Examination\nHall A",
        iconGlyph = "{ }"
      ),
      ExamCardItem(
        id = "mad",
        title = "Mobile Application Development",
        date = "22 Sep",
        time = "11 AM\n01 PM",
        location = "Examination\nHall A",
        icon = Icons.Outlined.PhoneAndroid
      )
    )
  }

  val pastExams = remember {
    listOf(
      ExamCardItem(
        id = "coa",
        title = "Computer Architecture & Organization",
        date = "14 Sep",
        time = "09 AM\n12 PM",
        location = "Examination\nHall B",
        iconGlyph = "COA",
        isCompleted = true
      ),
      ExamCardItem(
        id = "dm",
        title = "Discrete Mathematics & Graph Theory",
        date = "16 Sep",
        time = "09 AM\n12 PM",
        location = "Examination\nHall C",
        iconGlyph = "MATH",
        isCompleted = true
      )
    )
  }

  val currentList = if (selectedTab == ExamFilterTab.UPCOMING) upcomingExams else pastExams

  Scaffold(
    modifier = modifier
      .fillMaxSize()
      .testTag("exams_screen"),
    containerColor = MaterialTheme.invexColors.bgScreen,
    contentWindowInsets = WindowInsets(0, 0, 0, 0),
    snackbarHost = { SnackbarHost(snackbarHostState) },
    bottomBar = {
      BottomBar(
        currentTab = NavigationTab.EXAM,
        onTabSelected = onNavigateToTab,
        modifier = Modifier.padding(
          bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
        )
      )
    }
  ) { innerPadding ->
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .padding(horizontal = 20.dp)
        .testTag("exams_lazy_column")
    ) {
      item {
        Spacer(
          modifier = Modifier.height(
            WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 8.dp
          )
        )
      }

      // Header Section: Title "Exams"
      item {
        Text(
          text = "Exams",
          fontSize = 34.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.SansSerif,
          color = MaterialTheme.invexColors.textPrimary
        )
        Spacer(modifier = Modifier.height(14.dp))
      }

      // Filter Tabs (SegmentedTabPicker: Upcoming & Coming)
      item {
        Row(
          horizontalArrangement = Arrangement.spacedBy(10.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          ExamFilterTab.values().forEach { tab ->
            val isSelected = tab == selectedTab
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(if (isSelected) MaterialTheme.invexColors.bgDark else Color(0xFF888888))
                .clickable(
                  role = Role.Tab,
                  onClick = { selectedTab = tab }
                )
                .padding(horizontal = 24.dp, vertical = 9.dp)
                .testTag("tab_filter_${tab.name.lowercase()}"),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = tab.label,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                color = MaterialTheme.invexColors.textInverse
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(20.dp))
      }

      // Exam Detail Cards List
      items(currentList.size) { index ->
        val exam = currentList[index]
        ExamDetailCard(
          exam = exam,
          onViewSeatingClick = { onNavigateToSeating(exam.id) },
          modifier = Modifier.padding(bottom = 20.dp)
        )
      }

      // Bottom Spacer
      item {
        Spacer(
          modifier = Modifier.height(
            WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 24.dp
          )
        )
      }
    }
  }
}

/**
 * Detailed Exam Card strictly matching the Figma screenshot:
 * - Gradient header banner with circular white badge
 * - Course title
 * - 3-column metadata with Material Symbols (date_range, schedule, apartment)
 * - Full-width dark button with leading right arrow icon
 */
@Composable
fun ExamDetailCard(
  exam: ExamCardItem,
  onViewSeatingClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  val bannerBrush = if (!exam.isCompleted) {
    Brush.linearGradient(
      colors = listOf(
        Color(0xFF5EAA32),
        Color(0xFFA5DC50),
        Color(0xFFBFE754)
      )
    )
  } else {
    Brush.linearGradient(
      colors = listOf(Color(0xFFD4D4D4), Color(0xFFE8E8E8))
    )
  }

  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("exam_card_${exam.id}"),
    shape = RoundedCornerShape(MaterialTheme.invexDimens.radiusLg),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.invexColors.bgCardPrimary
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp)
    ) {
      // Banner Header with Overlapping White Badge
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(72.dp)
          .clip(RoundedCornerShape(16.dp))
          .background(bannerBrush)
          .padding(start = 12.dp),
        contentAlignment = Alignment.CenterStart
      ) {
        // Circular White Icon Badge
        Box(
          modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(Color.White),
          contentAlignment = Alignment.Center
        ) {
          if (exam.icon != null) {
            Icon(
              imageVector = exam.icon,
              contentDescription = null,
              tint = MaterialTheme.invexColors.textPrimary,
              modifier = Modifier.size(24.dp)
            )
          } else if (exam.iconGlyph != null) {
            Text(
              text = exam.iconGlyph,
              fontSize = 14.sp,
              fontWeight = FontWeight.Bold,
              fontFamily = FontFamily.Monospace,
              color = MaterialTheme.invexColors.textPrimary
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Course Title
      Text(
        text = exam.title,
        fontSize = 17.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.SansSerif,
        color = MaterialTheme.invexColors.textPrimary,
        lineHeight = 22.sp
      )

      Spacer(modifier = Modifier.height(10.dp))

      HorizontalDivider(
        thickness = 1.dp,
        color = MaterialTheme.invexColors.borderSubtle
      )

      Spacer(modifier = Modifier.height(12.dp))

      // 3-Column Schedule Metadata Row
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Column 1: Date
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Icon(
            imageVector = Icons.Outlined.DateRange,
            contentDescription = "Exam Date",
            tint = MaterialTheme.invexColors.textPrimary,
            modifier = Modifier.size(22.dp)
          )
          Text(
            text = exam.date,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.invexColors.textPrimary
          )
        }

        // Column 2: Time Slot
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Icon(
            imageVector = Icons.Outlined.Schedule,
            contentDescription = "Exam Time",
            tint = MaterialTheme.invexColors.textPrimary,
            modifier = Modifier.size(22.dp)
          )
          Text(
            text = exam.time,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.invexColors.textPrimary,
            lineHeight = 14.sp
          )
        }

        // Column 3: Location Hall
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Icon(
            imageVector = Icons.Outlined.Apartment,
            contentDescription = "Exam Location",
            tint = MaterialTheme.invexColors.textPrimary,
            modifier = Modifier.size(22.dp)
          )
          Text(
            text = exam.location,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.invexColors.textPrimary,
            lineHeight = 14.sp
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Action CTA Button: Full-width dark button with leading right arrow icon
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(52.dp)
          .clip(CircleShape)
          .background(if (!exam.isCompleted) Color(0xFF262626) else Color(0xFF666666))
          .clickable(
            role = Role.Button,
            onClick = onViewSeatingClick
          )
          .padding(horizontal = 14.dp)
          .testTag("btn_view_seating_${exam.id}"),
        contentAlignment = Alignment.Center
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically
        ) {
          // Circular white arrow container
          Box(
            modifier = Modifier
              .size(32.dp)
              .clip(CircleShape)
              .background(Color.White),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowForward,
              contentDescription = null,
              tint = Color(0xFF262626),
              modifier = Modifier.size(18.dp)
            )
          }

          // Centered Text
          Text(
            text = if (!exam.isCompleted) "view your seating" else "view past seating",
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            color = Color.White,
            modifier = Modifier.weight(1f),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
          )

          Spacer(modifier = Modifier.width(32.dp)) // balances the arrow on left
        }
      }
    }
  }
}

@Preview(
  name = "ExamsScreen Light Mode",
  showBackground = true,
  device = "spec:width=411dp,height=891dp,dpi=420"
)
@Composable
fun ExamsScreenPreview() {
  InvExTheme(darkTheme = false) {
    ExamsScreen()
  }
}
