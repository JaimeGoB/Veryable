package com.example.payments.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.payments.R
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
fun DisplayCreditAndDebitListsComponent(
    debitCards: MutableList<BankingCard>?,
    creditCards: MutableList<BankingCard>?
) {
    Scaffold(topBar = {
        AppBar(
            title = "ACCOUNTS",
            icon = Icons.Default.Home
        )
    }) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                DisplayDebitLists(debitCards)
                DisplayCreditLists(creditCards)
            }
        }
    }
}

@Composable
private fun DisplayCreditLists(creditCards: MutableList<BankingCard>?) {
    ListHeader("Cards")
    Divider(color = Color.LightGray)
    Column(
    ) {
        creditCards?.forEach { creditCard ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ShowBankingIcon(false, 24.dp)
                ShowBankingCard(creditCard, false)
            }
            Divider(color = Color.LightGray)
        }
    }
}

@Composable
private fun DisplayDebitLists(debitCards: MutableList<BankingCard>?) {
    Divider(color = Color.LightGray)
    ListHeader("Bank Accounts")
    Divider(color = Color.LightGray)
    Column(
    ) {
        debitCards?.forEach { debitCard ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ShowBankingIcon(true, 24.dp)
                ShowBankingCard(debitCard, true)
            }
            Divider(color = Color.LightGray)
        }
    }
}

@Composable
fun ShowBankingIcon(isDebit: Boolean, imageSize: Dp) {
    Card(
        modifier = Modifier.size(imageSize),
    ) {
        Icon(
            if(isDebit) { painterResource(R.drawable.bank) } else { painterResource(R.drawable.credit)} ,
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            tint = Color(0xFF0288d1),
        )
    }
}

@Composable
fun ShowBankingCard(bankingCard: BankingCard, isDebit: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        backgroundColor = Color.White,

        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Text(text = bankingCard.account_name, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            Text(
                text = bankingCard.desc, fontSize = 12.sp, modifier = Modifier
                    .alpha(0.85f)
                    .padding(top = 4.dp)
            )
            Text(
                text = if (isDebit) { "Bank Account: ACH - Same Day" } else { "Card: Instant" },
                    fontSize = 12.sp, modifier = Modifier
                    .alpha(0.50f)
                    .padding(top = 4.dp)
            )
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
            .background(Color(0xFFF5F5F5))
            .fillMaxWidth()
            .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
    ) {
        Text(
            text = title,
        )
    }
}