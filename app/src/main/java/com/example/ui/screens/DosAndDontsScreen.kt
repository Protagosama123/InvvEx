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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.Devices
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material.icons.outlined.LocalDrink
import androidx.compose.material.icons.outlined.Work
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
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

data class InstructionTileData(
  val title: String,
  val icon: ImageVector,
  val isAllowed: Boolean,
  val detailDescription: String,
  val testTag: String
)

/**
 * Screen 2: Do's and Don'ts Screen (Service - Do's and Don'ts)
 * Features "CARRY" and "AVOID" 2x2 grid tiles matching Figma reference.
 */
@Composable
fun DosAndDontsScreen(
  onBackClick: () -> Unit = {},
  modifier: Modifier = Modifier
) {
  BackHandler { onBackClick() }

  var selectedInstruction by remember { mutableStateOf<InstructionTileData?>(null) }

  val carryItems = remember {
    listOf(
      InstructionTileData(
        title = "Admit Card",
        icon = Icons.Outlined.Badge,
        isAllowed = true,
        detailDescription = "Printed physical admit card or authenticated digital verification copy is strictly mandatory for hall entry.",
        testTag = "tile_carry_admit_card"
      ),
      InstructionTileData(
        title = "Ball Pen",
        icon = Icons.Outlined.Edit,
        isAllowed = true,
        detailDescription = "Blue or black transparent ballpoint pens only. Gel pens and fountain pens are not recommended for OMR answer sheets.",
        testTag = "tile_carry_ball_pen"
      ),
      InstructionTileData(
        title = "Water Bottle",
        icon = Icons.Outlined.LocalDrink,
        isAllowed = true,
        detailDescription = "Only completely transparent bottles without labels or writing are allowed inside examination premises.",
        testTag = "tile_carry_water_bottle"
      ),
      InstructionTileData(
        title = "Calculator",
        icon = Icons.Outlined.Calculate,
        isAllowed = true,
        detailDescription = "Only non-programmable standard scientific calculators are permitted for engineering and math courses.",
        testTag = "tile_carry_calculator"
      )
    )
  }

  val avoidItems = remember {
    listOf(
      InstructionTileData(
        title = "Electronic\nDevices",
        icon = Icons.Outlined.Devices,
        isAllowed = false,
        detailDescription = "Smartphones, smartwatches, fitness bands, earphones, and digital gadgets must be deposited in the lobby lockers.",
        testTag = "tile_avoid_electronic_devices"
      ),
      InstructionTileData(
        title = "Paper\nNotes",
        icon = Icons.Outlined.Assignment,
        isAllowed = false,
        detailDescription = "Loose paper, notes, textbooks, cheat sheets, or scribbled stationery are strictly prohibited under university exam bylaws.",
        testTag = "tile_avoid_paper_notes"
      ),
      InstructionTileData(
        title = "Bags",
        icon = Icons.Outlined.Work,
        isAllowed = false,
        detailDescription = "Backpacks, pouches, and pencil cases must be kept outside the examination hall. Use transparent zip pouches.",
        testTag = "tile_avoid_bags"
      ),
      InstructionTileData(
        title = "Food",
        icon = Icons.Outlined.Fastfood,
        isAllowed = false,
        detailDescription = "Snacks, chewing gums, and packaged food items are not allowed inside the exam hall. Only personal medication with approval.",
        testTag = "tile_avoid_food"
      )
    )
  }

  Scaffold(
    modifier = modifier
      .fillMaxSize()
      .testTag("dos_and_donts_screen"),
    containerColor = MaterialTheme.invexColors.bgScreen,
    contentWindowInsets = WindowInsets(0, 0, 0, 0)
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
            .testTag("btn_dos_donts_back")
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
          text = "Do’s and Don’ts",
          fontSize = 30.sp,
          fontWeight = FontWeight.Bold,
          fontFamily = FontFamily.SansSerif,
          color = MaterialTheme.invexColors.textPrimary
        )
      }

      Spacer(modifier = Modifier.height(18.dp))

      // "CARRY" Section (Allowed Items)
      Text(
        text = "CARRY",
        fontSize = 20.sp,
        fontWeight = FontWeight.ExtraBold,
        letterSpacing = 0.5.sp,
        color = MaterialTheme.invexColors.textPrimary
      )

      Spacer(modifier = Modifier.height(14.dp))

      // 2x2 Grid for CARRY
      InstructionGrid2x2(
        items = carryItems,
        onTileClick = { selectedInstruction = it }
      )

      Spacer(modifier = Modifier.height(22.dp))

      // Visual Divider
      HorizontalDivider(
        thickness = 1.dp,
        color = MaterialTheme.invexColors.textPrimary.copy(alpha = 0.6f)
      )

      Spacer(modifier = Modifier.height(20.dp))

      // "AVOID" Section (Prohibited Items)
      Text(
        text = "AVOID",
        fontSize = 20.sp,
        fontWeight = FontWeight.ExtraBold,
        letterSpacing = 0.5.sp,
        color = MaterialTheme.invexColors.textPrimary
      )

      Spacer(modifier = Modifier.height(14.dp))

      // 2x2 Grid for AVOID
      InstructionGrid2x2(
        items = avoidItems,
        onTileClick = { selectedInstruction = it }
      )

      Spacer(
        modifier = Modifier.height(
          WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 24.dp
        )
      )
    }
  }

  // Instruction Detail Dialog
  selectedInstruction?.let { instruction ->
    AlertDialog(
      onDismissRequest = { selectedInstruction = null },
      icon = {
        Icon(
          imageVector = instruction.icon,
          contentDescription = null,
          tint = if (instruction.isAllowed) MaterialTheme.invexColors.textPrimary else MaterialTheme.invexColors.dangerRed,
          modifier = Modifier.size(36.dp)
        )
      },
      title = {
        Text(
          text = if (instruction.isAllowed) "Allowed: ${instruction.title}" else "Prohibited: ${instruction.title.replace("\n", " ")}",
          fontWeight = FontWeight.Bold
        )
      },
      text = {
        Text(instruction.detailDescription, lineHeight = 20.sp)
      },
      confirmButton = {
        TextButton(onClick = { selectedInstruction = null }) {
          Text("Got it", fontWeight = FontWeight.Bold, color = MaterialTheme.invexColors.textPrimary)
        }
      }
    )
  }
}

@Composable
fun InstructionGrid2x2(
  items: List<InstructionTileData>,
  onTileClick: (InstructionTileData) -> Unit
) {
  Column(
    verticalArrangement = Arrangement.spacedBy(14.dp),
    modifier = Modifier.fillMaxWidth()
  ) {
    // Row 1 (Items 0 and 1)
    if (items.size >= 2) {
      Row(
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        InstructionTile(
          item = items[0],
          onClick = { onTileClick(items[0]) },
          modifier = Modifier.weight(1f)
        )
        InstructionTile(
          item = items[1],
          onClick = { onTileClick(items[1]) },
          modifier = Modifier.weight(1f)
        )
      }
    }

    // Row 2 (Items 2 and 3)
    if (items.size >= 4) {
      Row(
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        InstructionTile(
          item = items[2],
          onClick = { onTileClick(items[2]) },
          modifier = Modifier.weight(1f)
        )
        InstructionTile(
          item = items[3],
          onClick = { onTileClick(items[3]) },
          modifier = Modifier.weight(1f)
        )
      }
    }
  }
}

/**
 * Grid Card Tile matching Figma reference:
 * - Light grey rectangular container (`#E8E8E8`) with rounded corners
 * - Top-right: Minimalist stroke icon
 * - Bottom-left: Bold text label
 */
@Composable
fun InstructionTile(
  item: InstructionTileData,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Box(
    modifier = modifier
      .height(132.dp)
      .clip(RoundedCornerShape(18.dp))
      .background(MaterialTheme.invexColors.bgCardSecondary)
      .clickable(
        role = Role.Button,
        onClick = onClick
      )
      .padding(16.dp)
      .testTag(item.testTag)
  ) {
    // Top-right Icon slot
    Icon(
      imageVector = item.icon,
      contentDescription = item.title,
      tint = MaterialTheme.invexColors.textPrimary,
      modifier = Modifier
        .size(34.dp)
        .align(Alignment.TopEnd)
    )

    // Bottom-left Text slot
    Text(
      text = item.title,
      fontSize = 17.sp,
      fontWeight = FontWeight.Bold,
      fontFamily = FontFamily.SansSerif,
      color = MaterialTheme.invexColors.textPrimary,
      lineHeight = 22.sp,
      modifier = Modifier.align(Alignment.BottomStart)
    )
  }
}

@Preview(
  name = "DosAndDontsScreen Light Mode",
  showBackground = true,
  device = "spec:width=411dp,height=891dp,dpi=420"
)
@Composable
fun DosAndDontsScreenPreview() {
  InvExTheme(darkTheme = false) {
    DosAndDontsScreen()
  }
}
