package com.holiware.world1.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(coins: List<Coin>)

    @Query("Select * from Coin")
    fun getCoins(): Flow<List<Coin>>

    @Query("Delete from Coin")
    suspend fun deleteAll()
}