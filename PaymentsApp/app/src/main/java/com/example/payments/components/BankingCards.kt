package com.example.payments.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
            PaymentApplication(debitCards, creditCards)
            // DisplayCreditAndDebitListsComponent(debitCards, creditCards)
            //debitCards?.get(0)
            //  ?.let { DisplayCardPaymentComponent(bankingCard = it, isDebit = true, 144.dp) }
        } else {
            // Display Error Screen
        }
    }
}

@Composable
fun PaymentApplication(
    debitCards: MutableList<BankingCard>?,
    creditCards: MutableList<BankingCard>?
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "payment_card_list") {
        composable("payment_card_list") {
            DisplayCreditAndDebitListsComponent(debitCards, creditCards, navController)
        }
        composable(
            route = "payment_card_details",
        ) { navBackStackEntry ->
            debitCards?.get(0)
                ?.let {
                    DisplayCardPaymentComponent(
                        bankingCard = it,
                        isDebit = true,
                        144.dp,
                        navController
                    )
                }
        }
    }
}

@Composable
fun DisplayCreditAndDebitListsComponent(
    debitCards: MutableList<BankingCard>?,
    creditCards: MutableList<BankingCard>?,
    navController: NavHostController
) {
    Scaffold(topBar = {
        AppBar(
            title = "ACCOUNTS",
            icon = Icons.Default.Home,
        ) {

        }
    }) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                DisplayPaymentsLists(debitCards, true, navController)
                DisplayPaymentsLists(creditCards, false, navController)
            }
        }
    }
}

@Composable
private fun DisplayPaymentsLists(
    paymentCards: MutableList<BankingCard>?,
    isDebit: Boolean,
    navController: NavHostController
) {

    if (isDebit) {
        Divider(color = Color.LightGray)
        ListHeader("Bank Accounts")
        Divider(color = Color.LightGray)
    } else {
        ListHeader("Cards")
        Divider(color = Color.LightGray)
    }

    Column(
    ) {
        paymentCards?.forEach { paymentCard ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 12.dp),
            ) {
                if (isDebit) {
                    ShowBankingIcon(true, 24.dp)
                    ShowBankingCard(paymentCard, true) {
                        navController?.navigate("payment_card_details")
                    }
                } else {
                    ShowBankingIcon(false, 24.dp)
                    ShowBankingCard(paymentCard, false) {
                        navController?.navigate("payment_card_details")
                    }
                }
            }
            Divider(color = Color.LightGray)
        }
    }
}

@Composable
fun ShowBankingIcon(isDebit: Boolean, imageSize: Dp) {
    Card(
        modifier = Modifier.size(imageSize),
        elevation = 0.dp
    ) {
        Icon(
            if (isDebit) {
                painterResource(R.drawable.bank)
            } else {
                painterResource(R.drawable.credit)
            },
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            tint = Color(0xFF0288d1),
        )
    }
}

@Composable
fun ShowBankingCard(bankingCard: BankingCard, isDebit: Boolean, clickAction: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { clickAction.invoke() }),
        backgroundColor = Color.White,
        elevation = 0.dp
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
                text = if (isDebit) {
                    "Bank Account: ACH - Same Day"
                } else {
                    "Card: Instant"
                },
                fontSize = 12.sp, modifier = Modifier
                    .alpha(0.50f)
                    .padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun AppBar(title: String, icon: ImageVector, iconClickAction: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            Icon(
                imageVector = icon,
                modifier = Modifier
                    .padding(12.dp)
                    .clickable(onClick = { iconClickAction.invoke() }),
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


@Composable
fun DisplayCardPaymentComponent(
    bankingCard: BankingCard,
    isDebit: Boolean,
    imageSize: Dp,
    navController: NavHostController
) {

    Scaffold(
        topBar = {
            AppBar(
                title = "DETAILS",
                icon = Icons.Default.ArrowBack,
            ) {
                navController?.navigateUp()
            }
        }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFFEDEDED)
        ) {
            PaymentDetails(imageSize, isDebit, bankingCard)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, bottom = 24.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                ButtonWithColor(navController) {
                    navController?.navigateUp()
                }
            }
        }
    }
}

@Composable
fun ButtonWithColor(navController: NavHostController, clickAction: () -> Unit) {
    Button(
        onClick = {
            clickAction.invoke()
        },
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0288d1))
    )

    {

        Text(
            text = "DONE",
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun PaymentDetails(
    imageSize: Dp,
    isDebit: Boolean,
    bankingCard: BankingCard
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.size(imageSize),
            backgroundColor = Color(0xFFEDEDED),
            elevation = 0.dp
        ) {
            Icon(
                if (isDebit) {
                    painterResource(R.drawable.bank_large)
                } else {
                    painterResource(R.drawable.credit_large)
                },
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                tint = Color(0xFF0288d1),
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (bankingCard != null) {
                Text(
                    text = bankingCard.account_name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            if (bankingCard != null) {
                Text(
                    text = bankingCard.desc, fontSize = 12.sp, modifier = Modifier
                        .alpha(0.85f)
                        .padding(top = 4.dp)
                )
            }
        }
    }
}



