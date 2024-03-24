package com.example.forecast.ui.watchlists

import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import com.example.forecast.ui.custompicker.CustomPicker
import com.example.forecast.ui.custompicker.PickerItem

@Composable
fun WatchlistsScreen() {
    val items = listOf(
        PickerItem("All", Icons.Default.Language),
        PickerItem("Convictions", Icons.Default.Pets),
        PickerItem("Events", Icons.Default.CalendarMonth),
        PickerItem("Markets", Icons.Default.Storefront),
        PickerItem("Disputes", Icons.Default.Gavel),
        PickerItem("Transactions", Icons.Default.CurrencyExchange),
        PickerItem("Transactions", Icons.Default.Money)
    )
    val dropdownOptions = listOf("For You", "Following") // Define the dropdown options

    CustomPicker(
        items = items,
        dropdownOptions = dropdownOptions,
        showLabel = false,
        showIcon = true,

    )
}