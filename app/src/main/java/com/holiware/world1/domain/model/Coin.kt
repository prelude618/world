package com.holiware.world1.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Coin")
data class Coin(
    @PrimaryKey
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    @SerializedName("current_price")
    val price: Double,
    @SerializedName("price_change_24h")
    val priceChange: Double,
    val sortOrder: Int = 0
)
