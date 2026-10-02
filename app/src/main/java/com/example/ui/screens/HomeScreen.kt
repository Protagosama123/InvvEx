package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.BottomBar
import com.example.ui.components.HeaderGreeting
import com.example.ui.components.NavigationTab
import com.example.ui.components.NoticeItemData
import com.example.ui.components.NoticeSection
import com.example.ui.components.ServicesGrid
import com.example.ui.components.SwipeToDismissBanner
import com.example.ui.components.TodayExamCard
import com.example.ui.components.TopNav
import com.example.ui.theme.InvExTheme
import com.example.ui.theme.invexColors
import kotlinx.coroutines.launch

enum class AppScreen {
  HOME,
  ADMIT_CARD,
  DOS_AND_DONTS,
  RESULTS,
  MEDICAL_LEAVE,
  EXAMS,
  ROOM_SEATING,
  SCHEDULE,
  SETTINGS,
  ACCOUNT_INFO,
  THEME,
  LANGUAGE,
  NOTIFICATIONS
}

/**
 * Root Application Screen managing full navigation across:
 * - Home Dashboard
 * - Exams List & Room Seating View (7x6 Matrix)
 * - Schedule Timeline (Week / Day / Month)
 * - Core Services (Admit Card, Do's & Don'ts, Results, Medical Leave)
 * - Settings (Account Info, Theme, Language, Notifications)
 */
@Composable
fun HomeScreen(
  modifier: Modifier = Modifier
) {
  var currentScreen by remember { mutableStateOf(AppScreen.HOME) }
  var selectedExamId by remember { mutableStateOf("oops") }

  AnimatedContent(
    targetState = currentScreen,
    transitionSpec = { fadeIn() togetherWith fadeOut() },
    label = "ScreenTransition"
  ) { screen ->
    when (screen) {
      AppScreen.HOME -> {
        HomeDashboard(
          onNavigateToAdmitCard = { currentScreen = AppScreen.ADMIT_CARD },
          onNavigateToDosDonts = { currentScreen = AppScreen.DOS_AND_DONTS },
          onNavigateToResults = { currentScreen = AppScreen.RESULTS },
          onNavigateToMedicalLeave = { currentScreen = AppScreen.MEDICAL_LEAVE },
          onNavigateToExams = { currentScreen = AppScreen.EXAMS },
          onNavigateToSchedule = { currentScreen = AppScreen.SCHEDULE },
          onNavigateToSettings = { currentScreen = AppScreen.SETTINGS },
          modifier = modifier
        )
      }
      AppScreen.EXAMS -> {
        ExamsScreen(
          onNavigateToSeating = { examId ->
            selectedExamId = examId
            currentScreen = AppScreen.ROOM_SEATING
          },
          onNavigateToTab = { tab ->
            when (tab) {
              NavigationTab.HOME -> currentScreen = AppScreen.HOME
              NavigationTab.EXAM -> Unit
              NavigationTab.SCHEDULE -> currentScreen = AppScreen.SCHEDULE
              NavigationTab.USER -> currentScreen = AppScreen.SETTINGS
            }
          },
          onBackClick = { currentScreen = AppScreen.HOME },
          modifier = modifier
        )
      }
      AppScreen.ROOM_SEATING -> {
        val (room, block, course) = when (selectedExamId) {
          "ads" -> Triple("318", "B", "ADS")
          "mad" -> Triple("204", "A", "MAD")
          else -> Triple("317", "B", "OOPS")
        }
        RoomSeatingScreen(
          onBackClick = { currentScreen = AppScreen.EXAMS },
          roomNumber = room,
          blockName = block,
          courseName = course,
          assignedSeatCoordinate = "D2",
          modifier = modifier
        )
      }
      AppScreen.ADMIT_CARD -> {
        AdmitCardScreen(
          onBackClick = { currentScreen = AppScreen.HOME },
          modifier = modifier
        )
      }
      AppScreen.DOS_AND_DONTS -> {
        DosAndDontsScreen(
          onBackClick = { currentScreen = AppScreen.HOME },
          modifier = modifier
        )
      }
      AppScreen.RESULTS -> {
        ResultsScreen(
          onBackClick = { currentScreen = AppScreen.HOME },
          modifier = modifier
        )
      }
      AppScreen.MEDICAL_LEAVE -> {
        MedicalLeaveScreen(
          onBackClick = { currentScreen = AppScreen.HOME },
          modifier = modifier
        )
      }
      AppScreen.SCHEDULE -> {
        ScheduleScreen(
          onNavigateToTab = { tab ->
            when (tab) {
              NavigationTab.HOME -> currentScreen = AppScreen.HOME
              NavigationTab.EXAM -> currentScreen = AppScreen.EXAMS
              NavigationTab.SCHEDULE -> Unit
              NavigationTab.USER -> currentScreen = AppScreen.SETTINGS
            }
          },
          onBackClick = { currentScreen = AppScreen.HOME },
          modifier = modifier
        )
      }
      AppScreen.SETTINGS -> {
        SettingsScreen(
          onNavigateToAccount = { currentScreen = AppScreen.ACCOUNT_INFO },
          onNavigateToTheme = { currentScreen = AppScreen.THEME },
          onNavigateToLanguage = { currentScreen = AppScreen.LANGUAGE },
          onNavigateToNotifications = { currentScreen = AppScreen.NOTIFICATIONS },
          onNavigateToTab = { tab ->
            when (tab) {
              NavigationTab.HOME -> currentScreen = AppScreen.HOME
              NavigationTab.EXAM -> currentScreen = AppScreen.EXAMS
              NavigationTab.SCHEDULE -> currentScreen = AppScreen.SCHEDULE
              NavigationTab.USER -> Unit
            }
          },
          onLogOut = { currentScreen = AppScreen.HOME },
          modifier = modifier
        )
      }
      AppScreen.ACCOUNT_INFO -> {
        AccountInfoScreen(
          onBackClick = { currentScreen = AppScreen.SETTINGS },
          onCancelClick = { currentScreen = AppScreen.SETTINGS },
          onSaveSuccess = { currentScreen = AppScreen.SETTINGS },
          modifier = modifier
        )
      }
      AppScreen.THEME -> {
        ThemeScreen(
          onBackClick = { currentScreen = AppScreen.SETTINGS },
          onCancelClick = { currentScreen = AppScreen.SETTINGS },
          onSaveTheme = { currentScreen = AppScreen.SETTINGS },
          modifier = modifier
        )
      }
      AppScreen.LANGUAGE -> {
        LanguageScreen(
          onBackClick = { currentScreen = AppScreen.SETTINGS },
          onCancelClick = { currentScreen = AppScreen.SETTINGS },
          onSaveLanguage = { currentScreen = AppScreen.SETTINGS },
          modifier = modifier
        )
      }
      AppScreen.NOTIFICATIONS -> {
        NotificationsScreen(
          onBackClick = { currentScreen = AppScreen.SETTINGS },
          onCancelClick = { currentScreen = AppScreen.SETTINGS },
          onSavePreferences = { currentScreen = AppScreen.SETTINGS },
          modifier = modifier
        )
      }
    }
  }
}

/**
 * Home Dashboard containing header, banner micro-interaction,
 * Today Exam progress widget, 4 service buttons, and notice feeds.
 */
@Composable
fun HomeDashboard(
  onNavigateToAdmitCard: () -> Unit,
  onNavigateToDosDonts: () -> Unit,
  onNavigateToResults: () -> Unit,
  onNavigateToMedicalLeave: () -> Unit,
  onNavigateToExams: () -> Unit,
  onNavigateToSchedule: () -> Unit,
  onNavigateToSettings: () -> Unit,
  modifier: Modifier = Modifier
) {
  val coroutineScope = rememberCoroutineScope()
  val snackbarHostState = remember { SnackbarHostState() }

  // State Management
  var isBannerVisible by remember { mutableStateOf(true) }
  var currentTab by remember { mutableStateOf(NavigationTab.HOME) }

  // Notice Data matching Figma specs
  val broadcastItems = remember {
    listOf(
      NoticeItemData("b1", "Lorem ipsum dolor sit amet, consectetur elit..."),
      NoticeItemData("b2", "Lorem ipsum dolor sit amet, consectetur elit...", isWarning = true),
      NoticeItemData("b3", "Lorem ipsum dolor sit amet, consectetur elit..."),
      NoticeItemData("b4", "Lorem ipsum dolor sit amet, consectetur elit...")
    )
  }

  val circularItems = remember {
    listOf(
      NoticeItemData("c1", "Lorem ipsum dolor sit amet, consectetur elit..."),
      NoticeItemData("c2", "Lorem ipsum dolor sit amet, consectetur elit...", isWarning = true),
      NoticeItemData("c3", "Lorem ipsum dolor sit amet, consectetur elit..."),
      NoticeItemData("c4", "Lorem ipsum dolor sit amet, consectetur elit...")
    )
  }

  Scaffold(
    modifier = modifier
      .fillMaxSize()
      .testTag("home_screen_scaffold"),
    containerColor = MaterialTheme.invexColors.bgScreen,
    contentWindowInsets = WindowInsets(0, 0, 0, 0),
    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    bottomBar = {
      BottomBar(
        currentTab = currentTab,
        onTabSelected = { tab ->
          currentTab = tab
          when (tab) {
            NavigationTab.HOME -> Unit
            NavigationTab.EXAM -> onNavigateToExams()
            NavigationTab.SCHEDULE -> onNavigateToSchedule()
            NavigationTab.USER -> onNavigateToSettings()
          }
        },
        modifier = Modifier.padding(
          bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
        )
      )
    }
  ) { paddingValues ->
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .testTag("home_screen_lazy_column")
    ) {
      // Status bar spacer
      item {
        Spacer(
          modifier = Modifier.height(
            WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
          )
        )
      }

      // 1. Header Navigation Bar (TopNav)
      item {
        TopNav(
          onNotificationsClick = {
            coroutineScope.launch {
              snackbarHostState.showSnackbar("You have 2 upcoming examination alerts.")
            }
          },
          onProfileClick = onNavigateToSettings
        )
      }

      // 2. User Greeting with inline pen graphic ("Stay ready, your ≡✎ exam awaits")
      item {
        HeaderGreeting()
      }

      // 3. Interactive Notification Banner Card (Swipe-to-dismiss & Direct close)
      item {
        SwipeToDismissBanner(
          visible = isBannerVisible,
          onDismiss = {
            isBannerVisible = false
            coroutineScope.launch {
              snackbarHostState.showSnackbar("Exam alert banner dismissed")
            }
          },
          onBadgeClick = { course ->
            if (course == "OOPS") {
              onNavigateToAdmitCard()
            } else {
              onNavigateToResults()
            }
          }
        )

        // Banner restore chip when dismissed
        if (!isBannerVisible) {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 20.dp, vertical = 4.dp),
            contentAlignment = Alignment.CenterEnd
          ) {
            AssistChip(
              onClick = { isBannerVisible = true },
              label = { Text("Restore Alert Banner", fontSize = 12.sp) },
              colors = AssistChipDefaults.assistChipColors(
                containerColor = MaterialTheme.invexColors.bgCardPrimary,
                labelColor = MaterialTheme.invexColors.textPrimary
              ),
              shape = RoundedCornerShape(12.dp)
            )
          }
        }
      }

      // 4. Today Exam Progress Card
      item {
        TodayExamCard(
          courseName = "Object oriented programming in java",
          courseId = "1234567890",
          startingIn = "0h 15m",
          completedExams = 1,
          totalExams = 5,
          onViewExamsClick = onNavigateToExams
        )
      }

      // 5. Top Service Entry Cards (Admit Card, Do's & Don'ts, Results, Medical Leave)
      item {
        ServicesGrid(
          onAdmitCardClick = onNavigateToAdmitCard,
          onDosDontsClick = onNavigateToDosDonts,
          onResultsClick = onNavigateToResults,
          onMedicalLeaveClick = onNavigateToMedicalLeave
        )
      }

      // 6. Broadcasts Section
      item {
        NoticeSection(
          title = "Broadcasts",
          items = broadcastItems,
          viewAllLabel = "View all Broadcasts",
          testTagPrefix = "broadcasts",
          onItemClick = { item ->
            coroutineScope.launch {
              snackbarHostState.showSnackbar("Broadcast Notice: ${item.text.take(30)}...")
            }
          },
          onViewAllClick = {
            coroutineScope.launch {
              snackbarHostState.showSnackbar("Opening all university broadcasts")
            }
          }
        )
      }

      // 7. Circulars Section
      item {
        NoticeSection(
          title = "Circulars",
          items = circularItems,
          viewAllLabel = "View all Circulars",
          testTagPrefix = "circulars",
          onItemClick = { item ->
            coroutineScope.launch {
              snackbarHostState.showSnackbar("Circular Notice: ${item.text.take(30)}...")
            }
          },
          onViewAllClick = {
            coroutineScope.launch {
              snackbarHostState.showSnackbar("Opening all university circulars")
            }
          }
        )
      }

      // Bottom clearance
      item {
        Spacer(modifier = Modifier.height(18.dp))
      }
    }
  }
}

// ==========================================
// Previews for Light Mode & Modular Sections
// ==========================================

@Preview(
  name = "HomeScreen - Full Light Mode",
  showBackground = true,
  device = "spec:width=411dp,height=891dp,dpi=420"
)
@Composable
fun HomeScreenPreview() {
  InvExTheme(darkTheme = false) {
    HomeScreen()
  }
}

@Preview(
  name = "HomeScreen - Banner Dismissed State",
  showBackground = true,
  device = "spec:width=411dp,height=891dp,dpi=420"
)
@Composable
fun HomeScreenBannerDismissedPreview() {
  InvExTheme(darkTheme = false) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.invexColors.bgScreen)
    ) {
      TopNav()
      HeaderGreeting()
      TodayExamCard()
      ServicesGrid(
        onAdmitCardClick = {},
        onDosDontsClick = {},
        onResultsClick = {},
        onMedicalLeaveClick = {}
      )
    }
  }
}
