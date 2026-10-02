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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
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

/**
 * Screen: Exam Subscreen - Room Seating Arrangement
 * Features a 7x6 seating matrix grid (A1 to G6), highlighting assigned seat D2 in lime green.
 */
@Composable
fun RoomSeatingScreen(
  onBackClick: () -> Unit = {},
  roomNumber: String = "317",
  blockName: String = "B",
  courseName: String = "OOPS",
  assignedSeatCoordinate: String = "D2",
  modifier: Modifier = Modifier
) {
  BackHandler { onBackClick() }

  val coroutineScope = rememberCoroutineScope()
  val snackbarHostState = remember { SnackbarHostState() }

  val rows = listOf("A", "B", "C", "D", "E", "F", "G")
  val columns = (1..6).toList()

  Scaffold(
    modifier = modifier
      .fillMaxSize()
      .testTag("room_seating_screen"),
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

      // 1. Header Section: Back Arrow + Page Title
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
      ) {
        IconButton(
          onClick = onBackClick,
          modifier = Modifier
            .size(40.dp)
            .testTag("btn_room_seating_back")
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
          text = "Room",
          fontSize = 34.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.SansSerif,
          color = MaterialTheme.invexColors.textPrimary
        )
      }

      Spacer(modifier = Modifier.height(6.dp))

      // Metadata Subtitle Bar: Pipe-separated string matching Figma
      Text(
        text = "Room : $roomNumber | Block : $blockName | Course : $courseName",
        fontSize = 17.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = FontFamily.SansSerif,
        color = Color(0xFF666666),
        modifier = Modifier.padding(start = 6.dp)
      )

      Spacer(modifier = Modifier.height(20.dp))

      // 2. Room Layout Grid Area (SeatingMapGrid 7x6 Matrix)
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("seating_map_grid_card"),
        shape = RoundedCornerShape(MaterialTheme.invexDimens.radiusLg),
        colors = CardDefaults.cardColors(
          containerColor = MaterialTheme.invexColors.bgCardPrimary
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp, horizontal = 12.dp),
          verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
          rows.forEach { rowLetter ->
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              columns.forEach { colNumber ->
                val seatCoord = "$rowLetter$colNumber"
                val isYourSeat = seatCoord == assignedSeatCoordinate

                SeatTile(
                  coordinate = seatCoord,
                  isYourSeat = isYourSeat,
                  onClick = {
                    coroutineScope.launch {
                      if (isYourSeat) {
                        snackbarHostState.showSnackbar("Seat $seatCoord: This is your assigned seat!")
                      } else {
                        snackbarHostState.showSnackbar("Seat $seatCoord is allocated to another candidate.")
                      }
                    }
                  }
                )
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(20.dp))

      // 3. Legend Section (SeatingLegend)
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("seating_legend_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
          containerColor = MaterialTheme.invexColors.bgCardPrimary
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
      ) {
        Row(
          modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 14.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
          // Highlighted lime-green icon tile
          Box(
            modifier = Modifier
              .size(54.dp)
              .clip(RoundedCornerShape(12.dp))
              .background(MaterialTheme.invexColors.accentLimeLight),
            contentAlignment = Alignment.Center
          ) {
            ArmchairGraphic(
              color = Color(0xFF1B4314),
              modifier = Modifier.size(30.dp)
            )
          }

          Text(
            text = "Your Seat",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            color = MaterialTheme.invexColors.textPrimary
          )
        }
      }

      Spacer(
        modifier = Modifier.height(
          WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 24.dp
        )
      )
    }
  }
}

/**
 * Individual Seat Tile:
 * - Default: Soft grey container (#EEEEEE), dark chair outline, coordinate label below.
 * - Your Seat (D2): Bright lime-green fill (#D4E795 / #E2F1A7), dark green chair,
 *   "YOUR SEAT" text overlay, bold coordinate label below.
 */
@Composable
fun SeatTile(
  coordinate: String,
  isYourSeat: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier.width(46.dp)
  ) {
    if (isYourSeat) {
      // Highlighted "YOUR SEAT" container
      Box(
        modifier = Modifier
          .size(46.dp)
          .clip(RoundedCornerShape(10.dp))
          .background(MaterialTheme.invexColors.accentLimeLight)
          .border(1.5.dp, Color(0xFF9ABF42), RoundedCornerShape(10.dp))
          .clickable(
            role = Role.Button,
            onClick = onClick
          )
          .padding(2.dp)
          .testTag("seat_tile_$coordinate"),
        contentAlignment = Alignment.Center
      ) {
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center
        ) {
          Text(
            text = "YOUR",
            fontSize = 7.5.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF1B4314),
            lineHeight = 8.sp
          )

          ArmchairGraphic(
            color = Color(0xFF1B4314),
            modifier = Modifier.size(20.dp)
          )

          Text(
            text = "SEAT",
            fontSize = 7.5.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF1B4314),
            lineHeight = 8.sp
          )
        }
      }
    } else {
      // Default soft grey seat container
      Box(
        modifier = Modifier
          .size(46.dp)
          .clip(RoundedCornerShape(10.dp))
          .background(Color(0xFFEEEEEE))
          .clickable(
            role = Role.Button,
            onClick = onClick
          )
          .testTag("seat_tile_$coordinate"),
        contentAlignment = Alignment.Center
      ) {
        ArmchairGraphic(
          color = Color.Black,
          modifier = Modifier.size(26.dp)
        )
      }
    }

    Spacer(modifier = Modifier.height(4.dp))

    // Coordinate Label under seat
    Text(
      text = coordinate,
      fontSize = 11.sp,
      fontWeight = if (isYourSeat) FontWeight.ExtraBold else FontWeight.Bold,
      fontFamily = FontFamily.SansSerif,
      color = MaterialTheme.invexColors.textPrimary
    )
  }
}

/**
 * Custom Armchair Vector Graphic strictly matching the Figma armchair silhouette
 */
@Composable
fun ArmchairGraphic(
  color: Color,
  modifier: Modifier = Modifier
) {
  Canvas(modifier = modifier) {
    val w = size.width
    val h = size.height
    val strokeWidth = 2.dp.toPx()

    // Backrest (Top horizontal curved bar)
    drawRoundRect(
      color = color,
      topLeft = Offset(w * 0.22f, h * 0.15f),
      size = Size(w * 0.56f, h * 0.35f),
      cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx()),
      style = Stroke(width = strokeWidth)
    )

    // Left Armrest
    drawRoundRect(
      color = color,
      topLeft = Offset(w * 0.08f, h * 0.32f),
      size = Size(w * 0.18f, h * 0.45f),
      cornerRadius = CornerRadius(3.dp.toPx(), 3.dp.toPx()),
      style = Stroke(width = strokeWidth)
    )

    // Right Armrest
    drawRoundRect(
      color = color,
      topLeft = Offset(w * 0.74f, h * 0.32f),
      size = Size(w * 0.18f, h * 0.45f),
      cornerRadius = CornerRadius(3.dp.toPx(), 3.dp.toPx()),
      style = Stroke(width = strokeWidth)
    )

    // Seat Cushion bottom line
    drawLine(
      color = color,
      start = Offset(w * 0.24f, h * 0.70f),
      end = Offset(w * 0.76f, h * 0.70f),
      strokeWidth = strokeWidth
    )

    // Left Front Leg
    drawLine(
      color = color,
      start = Offset(w * 0.22f, h * 0.75f),
      end = Offset(w * 0.18f, h * 0.92f),
      strokeWidth = strokeWidth
    )

    // Right Front Leg
    drawLine(
      color = color,
      start = Offset(w * 0.78f, h * 0.75f),
      end = Offset(w * 0.82f, h * 0.92f),
      strokeWidth = strokeWidth
    )
  }
}

@Preview(
  name = "RoomSeatingScreen Light Mode",
  showBackground = true,
  device = "spec:width=411dp,height=891dp,dpi=420"
)
@Composable
fun RoomSeatingScreenPreview() {
  InvExTheme(darkTheme = false) {
    RoomSeatingScreen()
  }
}
