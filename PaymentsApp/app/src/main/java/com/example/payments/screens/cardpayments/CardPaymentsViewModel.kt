package com.example.payments.screens.cardpayments

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.payments.data.DataOrException
import com.example.payments.model.BankingContext
import com.example.payments.repository.BankingCardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CardPaymentsViewModel @Inject constructor(private val repositoryBanking: BankingCardRepository) : ViewModel() {
    val response: MutableState<DataOrException<BankingContext, Boolean, Exception>> = mutableStateOf(
        DataOrException(null, true, Exception("") )
    )

    init {
        getAllCards()
    }

    private fun getAllCards() {
        viewModelScope.launch {
            // Update Loading status
            response.value.loading = true
            // Fetch Cards
            response.value = repositoryBanking.getAllCards()
            // Update Loading status after fetching
            //if(response.value.data.toString().isNotEmpty()){
            if(response.value.data?.debitCards.toString().isNotEmpty()){
                response.value.loading = false;
            }
        }
    }
}