package com.example.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Badge
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.FrontHand
import androidx.compose.material.icons.outlined.Sick
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.InvExTheme
import com.example.ui.theme.invexColors
import com.example.ui.theme.invexTypography

/**
 * Top service entry section displaying 4 circular action items:
 * 1. Admit Card
 * 2. Do's & Don'ts
 * 3. Results
 * 4. Medical Leave
 */
@Composable
fun ServicesGrid(
  onAdmitCardClick: () -> Unit = {},
  onDosDontsClick: () -> Unit = {},
  onResultsClick: () -> Unit = {},
  onMedicalLeaveClick: () -> Unit = {},
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 20.dp, vertical = 8.dp)
      .testTag("services_section")
  ) {
    // Section Title
    Text(
      text = "Services",
      style = MaterialTheme.invexTypography.headingLg,
      color = MaterialTheme.invexColors.textPrimary,
      modifier = Modifier.padding(bottom = 14.dp)
    )

    // Row of 4 Service Items
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.Top
    ) {
      ServiceItem(
        title = "Admit Card",
        icon = Icons.Outlined.Badge,
        testTag = "service_admit_card",
        onClick = onAdmitCardClick
      )

      ServiceItem(
        title = "Do's & Don'ts",
        icon = Icons.Outlined.FrontHand,
        testTag = "service_dos_donts",
        onClick = onDosDontsClick
      )

      ServiceItem(
        title = "Results",
        icon = Icons.Outlined.BarChart,
        testTag = "service_results",
        onClick = onResultsClick
      )

      ServiceItem(
        title = "Medical\nLeave",
        icon = Icons.Outlined.Sick,
        testTag = "service_medical_leave",
        onClick = onMedicalLeaveClick
      )
    }
  }
}

@Composable
fun ServiceItem(
  title: String,
  icon: ImageVector,
  testTag: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
      .width(76.dp)
      .testTag(testTag)
  ) {
    // Circular icon button
    Box(
      modifier = Modifier
        .size(68.dp)
        .clip(CircleShape)
        .background(MaterialTheme.invexColors.bgCardSecondary)
        .clickable(
          role = Role.Button,
          onClick = onClick
        ),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = icon,
        contentDescription = title,
        tint = MaterialTheme.invexColors.textPrimary,
        modifier = Modifier.size(32.dp)
      )
    }

    Spacer(modifier = Modifier.height(8.dp))

    // Label below
    Text(
      text = title,
      fontSize = 13.sp,
      fontWeight = FontWeight.Bold,
      color = MaterialTheme.invexColors.textPrimary,
      textAlign = TextAlign.Center,
      lineHeight = 16.sp,
      maxLines = 2
    )
  }
}

@Preview(showBackground = true, backgroundColor = 0xFFF2F2F2)
@Composable
fun ServicesGridPreview() {
  InvExTheme {
    ServicesGrid()
  }
}
