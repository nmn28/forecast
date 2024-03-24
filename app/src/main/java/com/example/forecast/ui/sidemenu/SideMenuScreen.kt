package com.example.forecast.ui.sidemenu

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

class SideMenuScreen {
    @Composable
    fun ExpandableNavigationRail() {
        var isExpanded by remember { mutableStateOf(false) }

        // Icons and labels for the items
        val items = listOf(
            Pair(Icons.Filled.CalendarToday, "Calendar"),
            Pair(Icons.Filled.Message, "Messages"),
            Pair(Icons.Filled.People, "Communion"),
            Pair(Icons.Filled.Science, "Chemistry"),
            Pair(Icons.Filled.Settings, "Settings")
        )

        val gestureDetector = Modifier.pointerInput(Unit) {
            detectTapGestures(onTap = {
                isExpanded = !isExpanded
            })
            // Add drag gesture detection if needed
        }

        Box(modifier = gestureDetector) {
            if (isExpanded) {
                ExpandedDrawerLayout(items)
            } else {
                CollapsedNavigationRail(items)
            }
        }
    }

    @Composable
    fun CollapsedNavigationRail(items: List<Pair<ImageVector, String>>) {
        NavigationRail {
            items.forEach { (icon, _) ->
                NavigationRailItem(
                    icon = { Icon(icon, contentDescription = null) },
                    label = null, // No label in collapsed state
                    selected = false,
                    onClick = { /* Handle item click */ }
                )
            }
        }
    }

    @Composable
    fun ExpandedDrawerLayout(items: List<Pair<ImageVector, String>>) {
        // Define the expanded drawer layout
        // This can be a Column with Icon and Text for each item
        Column(modifier = Modifier.width(250.dp)) { // Adjust the width as needed
            items.forEach { (icon, label) ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(icon, contentDescription = label)
                    Spacer(Modifier.width(8.dp))
                    Text(label)
                }
            }
        }
    }
}