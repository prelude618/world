package com.holiware.world1.model

import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    suspend fun fetchCoins(page: Int = 1)
    suspend fun getCoins(): Flow<List<Coin>>
}