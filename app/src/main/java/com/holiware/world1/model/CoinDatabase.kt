package com.holiware.world1.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Coin::class], version = 1, exportSchema = false)
abstract class CoinDatabase : RoomDatabase() {
    abstract fun getDao(): CoinDao
}