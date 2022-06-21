package com.example.payments.di

import com.example.payments.network.BankingCardApi
import com.example.payments.repository.BankingCardRepository
import com.example.payments.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCardRepository(api: BankingCardApi) = BankingCardRepository(api)

    @Singleton
    @Provides
    fun provideCardApi(): BankingCardApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BankingCardApi::class.java)
    }
}