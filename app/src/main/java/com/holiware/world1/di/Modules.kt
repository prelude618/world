package com.holiware.world1.di

import androidx.room.Room
import com.holiware.world1.model.CoinApi
import com.holiware.world1.model.CoinDatabase
import com.holiware.world1.model.CoinRepository
import com.holiware.world1.model.CoinRepositoryImpl
import com.holiware.world1.viewmodel.CoinViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModules = module {
    viewModel {
        CoinViewModel(get())
    }

    single<CoinRepository> {
        CoinRepositoryImpl(get(), get())
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.coingecko.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinApi::class.java)
    }

    single {
        Room.databaseBuilder(
            context = androidContext(),
            klass = CoinDatabase::class.java,
            name = "coin-database"
        )
            .fallbackToDestructiveMigration()
            .build()
            .getDao()
    }
}