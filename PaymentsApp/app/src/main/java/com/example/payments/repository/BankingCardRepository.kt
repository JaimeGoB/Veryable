package com.example.payments.repository

import android.util.Log
import com.example.payments.data.DataOrException
import com.example.payments.model.BankingContext
import com.example.payments.network.BankingCardApi
import javax.inject.Inject

class BankingCardRepository @Inject constructor(private val api: BankingCardApi) {
    private val dataOrException = DataOrException<BankingContext, Boolean, Exception>();

    suspend fun getAllCards(): DataOrException<BankingContext, Boolean, Exception> {
        try {
            // Update Loading status
            dataOrException.loading = true

            // Fetch Cards
            val response = api.getAllCards()

            // Update Loading status after fetching & Separate Card Types
            if (response.toString().isNotEmpty())  {
                dataOrException.loading = false
                // Map card context to its respective list
                dataOrException.data = BankingContext();

                val bank = response.filter { it.account_type.contains("bank") }
                dataOrException.data?.debitCards = ArrayList( bank )

                val card = response.filter { it.account_type.contains("card") }
                dataOrException.data?.creditCards = ArrayList( card )

                Log.d("BankingCardRepository", "getAllCards: Successfully fetched data from API")
            }

        } catch (exception: Exception) {
            // Add exception to metadata
            dataOrException.e = exception
            dataOrException.data = null
            Log.d("BankingCardRepository", "getAllQuestions: Exception  ${exception}")
        }
        return dataOrException;
    }
}