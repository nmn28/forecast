package com.example.forecast.ui.finances

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.forecast.ui.finances.wallet.CreditCard

class FinanceViewModel: ViewModel() {
    // Sample data - replace with actual logic to fetch or define this data
    val forecastCashCard = MutableLiveData(
        CreditCard(cardNumber = "1234 5678 9012 3456", holderName = "John Doe", balance = 1000.00, isForecastCash = true, expiryDate = "12/34")
    )

    val creditCards = MutableLiveData(
        listOf(
            CreditCard(cardNumber = "1234 5678 9012 3456", holderName = "John Doe", balance = 1000.00, isForecastCash = true, expiryDate = "12/34")
            // Add more cards as needed
        )
    )
}