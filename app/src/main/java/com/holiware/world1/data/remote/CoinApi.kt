package com.holiware.world1.data.remote

import com.holiware.world1.data.model.Coin
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinApi {
    @GET("api/v3/coins/markets")
    suspend fun fetchCoins(
        @Query("vs_currency") currency: String = "USD",
        @Query("order") order: String = "market_cap_desc",
        @Query("per_page") perPage: Int = 10,
        @Query("page") page: Int,
    ): List<Coin>
}
