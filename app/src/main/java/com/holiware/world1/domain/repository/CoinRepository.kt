package com.holiware.world1.domain.repository

import com.holiware.world1.domain.model.Coin

import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    suspend fun fetchCoins(page: Int = 1)
    fun getCoins(): Flow<List<Coin>>
}
