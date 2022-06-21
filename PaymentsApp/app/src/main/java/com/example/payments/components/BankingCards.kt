package com.example.payments.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.payments.model.BankingCard
import com.example.payments.screens.cardpayments.CardPaymentsViewModel

@Composable
fun BankingCards(viewModel: CardPaymentsViewModel) {
    // Get access to list
    val debitCards = viewModel.response.value.data?.debitCards?.toMutableList()
    val creditCards = viewModel.response.value.data?.creditCards?.toMutableList()


    if (viewModel.response.value.loading == true) {
        CircularProgressIndicator()
    } else {
        if (viewModel.response.value.data != null) {
            DisplayCreditAndDebitListsComponent(debitCards, creditCards)
        } else {
            // Display Error Screen
        }
    }
}

@Composable
fun DisplayCreditAndDebitListsComponent(debitCards: MutableList<BankingCard>?, creditCards: MutableList<BankingCard>?) {
    Scaffold(topBar = {
        AppBar(
            title = "Users List",
            icon = Icons.Default.Home
        )
    }) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column() {
                ListHeader("Bank Accounts")
                Column(
                    modifier = Modifier.padding(start = 4.dp, top = 8.dp, bottom = 8.dp)
                ) {
                    debitCards?.forEach { debitCard ->
                        ShowBankingCard(debitCard)
                    }
                }
                ListHeader("Cards")
                Column(
                    modifier = Modifier.padding(start = 4.dp, top = 8.dp, bottom = 8.dp)
                ) {
                    creditCards?.forEach { creditCard ->
                        ShowBankingCard(creditCard)
                    }
                }
            }
        }
    }
}

@Composable
fun ShowBankingCard(bankingCard: BankingCard) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = 8.dp,
        backgroundColor = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = bankingCard.account_name)
            Text(text = bankingCard.desc)
        }
    }
}

@Composable
fun AppBar(title: String, icon: ImageVector) {
    TopAppBar(
        navigationIcon = {
            Icon(
                imageVector = icon,
                modifier = Modifier.padding(12.dp),
                contentDescription = "Icon"
            )
        },
        title = {
            Text(
                text = title,
            )
        },
        backgroundColor = Color.White,
    )
}

@Composable
fun ListHeader(title: String) {
    Column(
        modifier = Modifier
            .background(Color.LightGray)
            .fillMaxWidth()
            .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
    ) {
        Text(
            text = title,
        )
    }
}