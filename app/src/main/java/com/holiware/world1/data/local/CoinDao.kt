package com.holiware.world1.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.holiware.world1.domain.model.Coin
import kotlinx.coroutines.flow.Flow

@Dao
interface CoinDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(coins: List<Coin>)

    @Query("SELECT * FROM Coin ORDER BY sortOrder ASC")
    fun getCoins(): Flow<List<Coin>>

    @Query("DELETE FROM Coin")
    suspend fun deleteAll()
}
