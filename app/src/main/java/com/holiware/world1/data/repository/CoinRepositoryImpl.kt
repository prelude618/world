package com.holiware.world1.data.repository

import com.holiware.world1.data.local.CoinDao
import com.holiware.world1.data.remote.CoinApi
import com.holiware.world1.domain.model.Coin
import com.holiware.world1.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow

class CoinRepositoryImpl(
    private val coinApi: CoinApi,
    private val coinDao: CoinDao,
): CoinRepository {
    override suspend fun fetchCoins(page: Int) {
        val firstSortOrder = ((page - 1) * PAGE_SIZE) + 1
        val coins = coinApi.fetchCoins(
            perPage = PAGE_SIZE,
            page = page
        )
        coinDao.insert(
            coins.mapIndexed { index, coin ->
                coin.copy(sortOrder = firstSortOrder + index)
            }
        )
    }

    override fun getCoins(): Flow<List<Coin>> = coinDao.getCoins()

    private companion object {
        const val PAGE_SIZE = 10
    }
}
