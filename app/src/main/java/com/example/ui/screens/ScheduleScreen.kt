package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Apartment
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.ui.components.BottomBar
import com.example.ui.components.NavigationTab
import com.example.ui.theme.InvExTheme
import com.example.ui.theme.invexColors
import com.example.ui.theme.invexDimens
import com.example.ui.theme.invexTypography

enum class ScheduleViewFilter(val label: String) {
  WEEK("Week"),
  DAY("Day"),
  MONTH("Month")
}

data class DayItem(
  val dayOfWeek: String,
  val dayNumber: Int,
  val isToday: Boolean = false,
  val hasEvents: Boolean = true
)

data class ScheduledSlot(
  val timeRange: String,
  val subject: String,
  val courseCode: String,
  val room: String,
  val building: String,
  val status: String,
  val statusColor: Color
)

/**
 * Screen: Tab Bar - Schedule View
 * Features date strip picker, timeline with hourly slots and vertical accent indicator cards.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
  onNavigateToTab: (NavigationTab) -> Unit = {},
  onBackClick: () -> Unit = {},
  modifier: Modifier = Modifier
) {
  BackHandler { onBackClick() }

  val coroutineScope = rememberCoroutineScope()
  val snackbarHostState = remember { SnackbarHostState() }

  var activeViewFilter by remember { mutableStateOf(ScheduleViewFilter.WEEK) }
  var selectedDayIndex by remember { mutableIntStateOf(2) } // Wednesday 16 by default
  var selectedSlotDetail by remember { mutableStateOf<ScheduledSlot?>(null) }
  var weekOffset by remember { mutableIntStateOf(0) }

  // Days of current week matching Figma ("Mon 14" to "Sat 19")
  val baseDays = remember(weekOffset) {
    val startDay = 14 + (weekOffset * 7)
    listOf(
      DayItem("Mon", startDay, hasEvents = false),
      DayItem("Tue", startDay + 1, hasEvents = false),
      DayItem("Wed", startDay + 2, isToday = true, hasEvents = true),
      DayItem("Thu", startDay + 3, hasEvents = false),
      DayItem("Fri", startDay + 4, hasEvents = true),
      DayItem("Sat", startDay + 5, hasEvents = false)
    )
  }

  // Pre-configured slots for the timeline
  val wednesdayEvents = remember {
    mapOf(
      "11:00 AM" to ScheduledSlot(
        timeRange = "11:00 AM - 12:00 PM",
        subject = "Object Oriented\nProgramming In Java",
        courseCode = "CS301",
        room = "Hall 302",
        building = "Academic Block C",
        status = "Exam Day",
        statusColor = Color(0xFF2E7D32)
      ),
      "02:00 PM" to ScheduledSlot(
        timeRange = "02:00 PM - 03:00 PM",
        subject = "Advanced Data\nStructures",
        courseCode = "CS302",
        room = "Lab 104",
        building = "Engineering Annex",
        status = "Upcoming",
        statusColor = Color(0xFFF59E0B)
      )
    )
  }

  val fridayEvents = remember {
    mapOf(
      "09:00 AM" to ScheduledSlot(
        timeRange = "09:00 AM - 11:00 AM",
        subject = "Discrete Mathematics\n& Graph Theory",
        courseCode = "MA301",
        room = "Room 201",
        building = "Science Complex",
        status = "Scheduled",
        statusColor = Color(0xFF262626)
      )
    )
  }

  // Timeline hours
  val hoursList = remember {
    listOf(
      "09:00 AM",
      "10:00 AM",
      "11:00 AM",
      "12:00 PM",
      "01:00 PM",
      "02:00 PM",
      "03:00 PM",
      "04:00 PM"
    )
  }

  val activeDay = baseDays.getOrElse(selectedDayIndex) { baseDays[0] }
  val activeEvents = when (activeDay.dayOfWeek) {
    "Wed" -> wednesdayEvents
    "Fri" -> fridayEvents
    else -> emptyMap()
  }

  Scaffold(
    modifier = modifier
      .fillMaxSize()
      .testTag("schedule_screen"),
    containerColor = MaterialTheme.invexColors.bgScreen,
    contentWindowInsets = WindowInsets(0, 0, 0, 0),
    snackbarHost = { SnackbarHost(snackbarHostState) },
    bottomBar = {
      BottomBar(
        currentTab = NavigationTab.SCHEDULE,
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
        .testTag("schedule_lazy_column")
    ) {
      item {
        Spacer(
          modifier = Modifier.height(
            WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 8.dp
          )
        )
      }

      // 1. Header Section: Title "Schedule"
      item {
        Text(
          text = "Schedule",
          fontSize = 34.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.SansSerif,
          color = MaterialTheme.invexColors.textPrimary
        )
        Spacer(modifier = Modifier.height(14.dp))
      }

      // Filter View Pill ("Week" selector matching Figma)
      item {
        Row(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          ScheduleViewFilter.values().forEach { filter ->
            val isSelected = filter == activeViewFilter
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(if (isSelected) MaterialTheme.invexColors.bgDark else MaterialTheme.invexColors.bgCardSecondary)
                .clickable(
                  role = Role.Tab,
                  onClick = { activeViewFilter = filter }
                )
                .padding(horizontal = 22.dp, vertical = 8.dp)
                .testTag("filter_chip_${filter.name.lowercase()}"),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = filter.label,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                color = if (isSelected) MaterialTheme.invexColors.textInverse else MaterialTheme.invexColors.textPrimary
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(18.dp))
      }

      // 2. Date Strip Picker (Mon 14 to Sat 19)
      item {
        Card(
          modifier = Modifier
            .fillMaxWidth()
            .testTag("date_strip_picker"),
          shape = RoundedCornerShape(18.dp),
          colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.invexColors.bgCardPrimary
          ),
          elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 12.dp, horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            // Left Chevron
            IconButton(
              onClick = { weekOffset-- },
              modifier = Modifier.size(36.dp)
            ) {
              Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "Previous week",
                tint = MaterialTheme.invexColors.textPrimary,
                modifier = Modifier.size(28.dp)
              )
            }

            // Days Strip
            baseDays.forEachIndexed { index, day ->
              val isSelected = index == selectedDayIndex

              if (isSelected) {
                // Highlighted Circular Pill (Black background with white text)
                Column(
                  modifier = Modifier
                    .size(width = 46.dp, height = 66.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.invexColors.bgDark)
                    .clickable { selectedDayIndex = index }
                    .padding(vertical = 6.dp)
                    .testTag("date_chip_selected"),
                  horizontalAlignment = Alignment.CenterHorizontally,
                  verticalArrangement = Arrangement.Center
                ) {
                  Text(
                    text = day.dayOfWeek,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.SansSerif,
                    color = MaterialTheme.invexColors.textInverse
                  )
                  Text(
                    text = "${day.dayNumber}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    color = MaterialTheme.invexColors.textInverse
                  )
                }
              } else {
                // Default Day Column (Grey day, dark date)
                Column(
                  modifier = Modifier
                    .size(width = 46.dp, height = 66.dp)
                    .clip(CircleShape)
                    .clickable { selectedDayIndex = index }
                    .padding(vertical = 6.dp)
                    .testTag("date_chip_${day.dayOfWeek.lowercase()}"),
                  horizontalAlignment = Alignment.CenterHorizontally,
                  verticalArrangement = Arrangement.Center
                ) {
                  Text(
                    text = day.dayOfWeek,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.SansSerif,
                    color = Color(0xFF777777)
                  )
                  Text(
                    text = "${day.dayNumber}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    color = MaterialTheme.invexColors.textPrimary
                  )
                }
              }
            }

            // Right Chevron
            IconButton(
              onClick = { weekOffset++ },
              modifier = Modifier.size(36.dp)
            ) {
              Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Next week",
                tint = MaterialTheme.invexColors.textPrimary,
                modifier = Modifier.size(28.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(28.dp))
      }

      // 3. Timeline Time Slots Grid (09:00 AM to 04:00 PM)
      items(hoursList.size) { index ->
        val hour = hoursList[index]
        val slotEvent = activeEvents[hour]

        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
          verticalAlignment = if (slotEvent != null) Alignment.CenterVertically else Alignment.CenterVertically
        ) {
          // Left Time Indicator
          Text(
            text = hour,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            color = MaterialTheme.invexColors.textPrimary,
            modifier = Modifier.width(90.dp)
          )

          Spacer(modifier = Modifier.width(8.dp))

          // Right slot content: Event card or horizontal divider line
          if (slotEvent != null) {
            // Scheduled Event Card matching Figma
            Card(
              modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .clickable { selectedSlotDetail = slotEvent }
                .testTag("event_card_${slotEvent.courseCode.lowercase()}"),
              shape = RoundedCornerShape(16.dp),
              colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.invexColors.bgCardPrimary
              ),
              elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                // Vertical Solid Black Bar Accent Indicator
                Box(
                  modifier = Modifier
                    .width(5.dp)
                    .height(42.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(Color.Black)
                )

                Spacer(modifier = Modifier.width(14.dp))

                // Subject Title
                Text(
                  text = slotEvent.subject,
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Bold,
                  fontFamily = FontFamily.SansSerif,
                  color = MaterialTheme.invexColors.textPrimary,
                  lineHeight = 22.sp
                )
              }
            }
          } else {
            // Empty slot horizontal divider line matching Figma
            HorizontalDivider(
              thickness = 1.dp,
              color = Color(0xFFC0C0C0),
              modifier = Modifier.fillMaxWidth()
            )
          }
        }
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

  // Event Detail Modal Bottom Sheet
  selectedSlotDetail?.let { event ->
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(
      onDismissRequest = { selectedSlotDetail = null },
      sheetState = sheetState,
      containerColor = MaterialTheme.invexColors.bgScreen
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(22.dp)
          .testTag("sheet_event_detail")
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(event.statusColor)
              .padding(horizontal = 12.dp, vertical = 6.dp)
          ) {
            Text(
              text = event.status,
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = Color.White
            )
          }

          IconButton(onClick = { selectedSlotDetail = null }) {
            Icon(Icons.Default.Close, contentDescription = "Close details")
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
          text = event.subject.replace("\n", " "),
          style = MaterialTheme.invexTypography.headingLg,
          color = MaterialTheme.invexColors.textPrimary
        )

        Text(
          text = "Course Code: ${event.courseCode}",
          fontSize = 14.sp,
          color = MaterialTheme.invexColors.textSecondary,
          modifier = Modifier.padding(top = 4.dp, bottom = 18.dp)
        )

        // Metadata rows mapping icon-mappings.md strictly
        ScheduleDetailRow(
          icon = Icons.Outlined.DateRange,
          label = "Date",
          value = "${activeDay.dayOfWeek}, Oct ${activeDay.dayNumber}, 2026"
        )
        ScheduleDetailRow(
          icon = Icons.Outlined.Schedule,
          label = "Time Slot",
          value = event.timeRange
        )
        ScheduleDetailRow(
          icon = Icons.Outlined.Apartment,
          label = "Location",
          value = "${event.room}, ${event.building}"
        )

        Spacer(modifier = Modifier.height(24.dp))
      }
    }
  }
}

@Composable
fun ScheduleDetailRow(
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  label: String,
  value: String
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 6.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Icon(
      imageVector = icon,
      contentDescription = label,
      tint = MaterialTheme.invexColors.textPrimary,
      modifier = Modifier.size(22.dp)
    )

    Spacer(modifier = Modifier.width(12.dp))

    Column {
      Text(
        text = label,
        fontSize = 11.sp,
        color = MaterialTheme.invexColors.textSecondary,
        fontWeight = FontWeight.Medium
      )
      Text(
        text = value,
        fontSize = 14.sp,
        color = MaterialTheme.invexColors.textPrimary,
        fontWeight = FontWeight.Bold
      )
    }
  }
}

@Preview(
  name = "ScheduleScreen Light Mode",
  showBackground = true,
  device = "spec:width=411dp,height=891dp,dpi=420"
)
@Composable
fun ScheduleScreenPreview() {
  InvExTheme(darkTheme = false) {
    ScheduleScreen()
  }
}
