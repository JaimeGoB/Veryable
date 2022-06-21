package com.example.payments.network

import com.example.payments.model.BankingCardsList
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface BankingCardApi {
    @GET("veryable.json")
    suspend fun getAllCards(): BankingCardsList
}