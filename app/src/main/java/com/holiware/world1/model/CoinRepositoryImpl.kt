package com.holiware.world1.model

import android.util.Log

class CoinRepositoryImpl(
    private val coinApi: CoinApi,
    private val coinDao: CoinDao,
): CoinRepository {
    override suspend fun fetchCoins(page: Int) {
        try {
            val coins = coinApi.fetchCoins(page = page)
            coinDao.insert(coins)
        } catch (e: Exception) {
            Log.e(Tag, "fetchCoins $e")
            throw e
        }
    }

    override suspend fun getCoins() = coinDao.getCoins()

    companion object {
        private const val Tag = "CoinRepositoryImpl"
    }
}