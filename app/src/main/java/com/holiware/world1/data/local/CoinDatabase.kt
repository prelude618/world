package com.holiware.world1.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.holiware.world1.data.model.Coin

@Database(entities = [Coin::class], version = 2, exportSchema = false)
abstract class CoinDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao
}
