package com.example.forecast.ui.finances


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.forecast.ui.custompicker.CustomPicker
import com.example.forecast.ui.custompicker.PickerItem
import com.example.forecast.ui.finances.wallet.CreditCard
import com.example.forecast.ui.finances.wallet.WalletScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FinancesScreen(navController: NavController) {
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )
    val coroutineScope = rememberCoroutineScope()

    // Sample data for WalletScreen
    val forecastCashCard = CreditCard(
        cardNumber = "1234 5678 9012 3456",
        holderName = "John Doe",
        balance = 1000.00,
        isForecastCash = true,
        expiryDate = "12/34"
    )
    val creditCards = listOf(forecastCashCard) // Assuming you have only one card for simplicity

    // OnCardSelected logic
    val onCardSelected: (CreditCard) -> Unit = { /* Handle card selection */ }

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize() // Make the sheet content fill the screen
                    .background(Color.White)
            ) {
                // Your full-screen content here
                AddCardScreen(navController = navController)
            }
        },
        sheetBackgroundColor = Color.Transparent,
        scrimColor = Color.Black.copy(alpha = 0.5f),
        sheetShape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(23.dp)) {
                // Deposit Column
                Column {
                    IconButton(onClick = { /* Navigate to Add Cash Details */ }) {
                        Icon(Icons.Filled.AddCircle, contentDescription = "Deposit")
                    }
                    Text("Deposit", fontSize = 12.sp)
                }
                // Withdraw Column
                Column {
                    IconButton(onClick = { /* Navigate to Send Cash Details */ }) {
                        Icon(Icons.Filled.RemoveCircle, contentDescription = "Withdraw")
                    }
                    Text("Withdraw", fontSize = 12.sp)
                }
                // Send Column
                Column {
                    IconButton(onClick = { /* Navigate to Send Cash Details */ }) {
                        Icon(Icons.Filled.Upload, contentDescription = "Send")
                    }
                    Text("Send", fontSize = 12.sp)
                }
                // Request Column
                Column {
                    IconButton(onClick = { /* Navigate to Add Receive Cash Details */ }) {
                        Icon(Icons.Filled.Download, contentDescription = "Request")
                    }
                    Text("Request", fontSize = 12.sp)
                }
                Column {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            if (modalBottomSheetState.isVisible) {
                                modalBottomSheetState.hide()
                            } else {
                                modalBottomSheetState.show()
                            }
                        }
                    }) {
                        Icon(Icons.Filled.CreditCard, contentDescription = "Add Card")
                    }
                    Text("Add Card", fontSize = 12.sp)
                }
            }

            // CustomPicker placed below the row of buttons
            val items = listOf(
                PickerItem("Wallet", Icons.Default.Wallet) { WalletScreen(viewModel = viewModel(), navController = navController)  },//navController.navigate(Screens.WALLET.name) },
                PickerItem("Investments", Icons.Default.Money) { },
                PickerItem("Analytics", Icons.Default.BarChart) { }
            )
            val dropdownOptions = listOf("For You", "Following") // Define the dropdown options
            CustomPicker(
                items = items,
                dropdownOptions = dropdownOptions,
                showLabel = false,
                showIcon = true,
            )
        }
    }
}