package com.holiware.world1.model

class CoinRepositoryImpl(
    private val coinApi: CoinApi,
    private val coinDao: CoinDao,
): CoinRepository {
    override suspend fun fetchCoins() {
        val coins = coinApi.fetchCoins(page = 1)
        coinDao.insert(coins)
    }

    override suspend fun getCoins() = coinDao.getCoins()
}