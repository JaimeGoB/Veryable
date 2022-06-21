package com.example.payments.screens.cardpayments

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.payments.components.BankingCards

@Composable
fun CardPaymentsScreen(viewModelBanking: CardPaymentsViewModel = hiltViewModel()) {
    BankingCards(viewModelBanking)
}