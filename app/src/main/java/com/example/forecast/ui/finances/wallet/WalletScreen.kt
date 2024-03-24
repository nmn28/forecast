package com.example.forecast.ui.finances.wallet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TransferWithinAStation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.forecast.Screens
import com.example.forecast.ui.finances.FinanceViewModel
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

@Composable
fun SelectedCreditCardScreen(
    creditCard: CreditCard,
    transactions: List<Transaction>,
    onDismiss: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Selected Credit Card") },
                navigationIcon = {
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        // Action for magnifying glass
                    }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                    if (creditCard.isForecastCash) {
                        ForecastCashCardMenu()
                    } else {
                        RegularCardMenu()
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            CreditCardScreen(creditCard = creditCard)
            TransactionsScreen(
                balance = creditCard.balance,
                transactions = transactions,
                creditCard = creditCard
            )
        }
    }
}

@Composable
private fun ForecastCashCardMenu() {
    val expanded = remember { mutableStateOf(false) }
    DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false }
    ) {
        DropdownMenuItem(onClick = {
            // Action specific to forecastCash card
        }) {
            Icon(Icons.Default.AttachMoney, contentDescription = "Add Money")
            Text("Add Money")
        }

        DropdownMenuItem(onClick = {
            // Another action specific to forecastCash card
        }) {
            Icon(Icons.Default.TransferWithinAStation, contentDescription = "Transfer to Bank")
            Text("Transfer to Bank")
        }

        DropdownMenuItem(onClick = {
            // Another action specific to forecastCash card
        }) {
            Icon(Icons.Default.Repeat, contentDescription = "Recurring Payments")
            Text("Recurring Payments")
        }

        DropdownMenuItem(onClick = {
            // Another action specific to forecastCash card
        }) {
            Icon(Icons.Default.CreditCard, contentDescription = "Card Number")
            Text("Card Number")
        }

        DropdownMenuItem(onClick = {
            // Another action specific to forecastCash card
        }) {
            Icon(Icons.Default.Info, contentDescription = "Card Details")
            Text("Card Details")
        }

        DropdownMenuItem(onClick = {
            // Another action specific to forecastCash card
        }) {
            Icon(Icons.Default.Notifications, contentDescription = "Notifications")
            Text("Notifications")
        }
    }
}

@Composable
private fun RegularCardMenu() {
    val expanded = remember { mutableStateOf(false) }
    DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false }
    ) {
        DropdownMenuItem(onClick = {
            // Another action specific to regular card
        }) {
            Icon(Icons.Default.CreditCard, contentDescription = "Card Number")
            Text("Card Number")
        }

        DropdownMenuItem(onClick = {
            // Another action specific to regular card
        }) {
            Icon(Icons.Default.Info, contentDescription = "Card Details")
            Text("Card Details")
        }

        DropdownMenuItem(onClick = {
            // Another action specific to regular card
        }) {
            Icon(Icons.Default.Notifications, contentDescription = "Notifications")
            Text("Notifications")
        }
    }
}

@Composable
fun CreditCardStackScreen(creditCards: List<CreditCard>) {
    Column {
        creditCards.forEachIndexed { index, card ->
            CreditCardScreen(
                creditCard = card,
                modifier = Modifier.offset(y = (index * 20).dp)
            )
        }
    }
}

@Composable
fun CreditCardScreen(creditCard: CreditCard, modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .then(modifier)
            .width(360.dp)
            .height(190.dp)
            .shadow(10.dp, RoundedCornerShape(15.dp))
            .background(
                brush = if (creditCard.isForecastCash) {
                    Brush.linearGradient(
                        colors = listOf(Color.Gray, Color.Black),
                        start = Offset(0f, 0f),
                        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                    )
                } else {
                    Brush.linearGradient(
                        colors = listOf(Color.Blue, Color.Magenta),
                        start = Offset(0f, 0f),
                        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                    )
                },
                shape = RoundedCornerShape(15.dp)
            )
            .border(
                width = if (creditCard.isForecastCash) 0.5.dp else 0.dp,
                color = if (creditCard.isForecastCash) Color.White else Color.Transparent,
                shape = RoundedCornerShape(15.dp)
            )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (creditCard.isForecastCash) {
                    Row(horizontalArrangement = Arrangement.spacedBy(1.dp)) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rays",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = "Cash",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                } else {
                    Text(
                        text = "BANK NAME",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                if (creditCard.isForecastCash) {
                    Text(
                        text = "$%.2f".format(creditCard.balance),
                        color = Color.White,
                        fontSize = 18.sp
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.CreditCard,
                        contentDescription = "Credit Card",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Text(
                text = creditCard.cardNumber,
                color = Color.White,
                fontSize = 18.sp
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "CARD HOLDER",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 12.sp
                    )
                    Text(
                        text = creditCard.holderName,
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "EXPIRES",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 12.sp
                    )
                    Text(
                        text = creditCard.expiryDate ?: "N/A",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}

data class CreditCard(
    val id: UUID = UUID.randomUUID(),
    val cardNumber: String,
    val holderName: String,
    val balance: Double,
    val isForecastCash: Boolean = false,
    val expiryDate: String? = null // Optional if some cards don't have an expiry date
)

@Preview
@Composable
fun CreditCardScreenPreview() {
    CreditCardScreen(
        creditCard = CreditCard(
            cardNumber = "**** **** **** 1234",
            holderName = "John Doe",
            balance = 1175.30
        )
    )
}

data class Transaction(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val amount: Double,
    val date: Date
)

val sampleTransactions = listOf(
    Transaction(title = "Coffee", amount = -3.50, date = Date()),
    Transaction(title = "Book", amount = -15.20, date = Date()),
    Transaction(title = "Salary", amount = 1200.00, date = Date())
    // Add more transactions as needed
)

@Composable
fun TransactionsScreen(
    balance: Double = 1175.30,
    transactions: List<Transaction>,
    creditCard: CreditCard
) {
    Column {
        // Your existing setup for the credit card view and current balance header

        Text(
            text = "Recent Transactions",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        // Use LazyColumn for transactions
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(1.dp, Color.Blue, RoundedCornerShape(10.dp))
        ) {
            items(transactions) { transaction ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = transaction.title,
                                style = MaterialTheme.typography.subtitle1
                            )
                            Text(
                                text = "%+.2f".format(transaction.amount),
                                style = MaterialTheme.typography.subtitle1,
                                fontWeight = FontWeight.Bold,
                                color = if (transaction.amount < 0) Color.Red else Color.Green
                            )
                            Icon(
                                imageVector = Icons.Default.ChevronRight,
                                contentDescription = null
                            )
                        }
                        Text(
                            text = itemFormatter.format(transaction.date),
                            style = MaterialTheme.typography.body2,
                            color = Color.Gray
                        )
                    }
                }
                Divider(
                    modifier = Modifier.padding(start = 15.dp),
                    thickness = 1.dp
                )
            }
        }
    }
}

// Date formatter
private val itemFormatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())

@Preview
@Composable
fun TransactionsScreenPreview() {
    // Create a sample credit card instance
    val sampleCreditCard = CreditCard(
        cardNumber = "**** **** **** 1234",
        holderName = "John Doe",
        balance = 1175.30
    )

    // Pass this sample credit card to the TransactionsView
    TransactionsScreen(
        balance = 1175.30,
        transactions = sampleTransactions,
        creditCard = sampleCreditCard
    )
}

@Composable
fun WalletScreen(viewModel: FinanceViewModel, navController: NavController) {
    val forecastCashCard by viewModel.forecastCashCard.observeAsState()
    val creditCards by viewModel.creditCards.observeAsState(initial = listOf())


    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            forecastCashCard?.let { card ->
                CreditCardScreen(
                    creditCard = card,
                    modifier = Modifier.clickable {
                        navigateToSelectedCard(navController, card)
                    }
                )
            }
        }
        items(creditCards) { card ->
            CreditCardScreen(
                creditCard = card,
                modifier = Modifier.clickable {
                    navigateToSelectedCard(navController, card)
                }
            )
        }
    }
}

private fun navigateToSelectedCard(navController: NavController, card: CreditCard?) {
    card?.let {
        val cardJson = Gson().toJson(it)
        navController.navigate("${Screens.CREDIT_CARD.name}?creditCardJson=$cardJson")
    }
}
