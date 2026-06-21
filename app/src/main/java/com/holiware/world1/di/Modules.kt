package com.holiware.world1.di

import androidx.room.Room
import com.holiware.world1.data.local.CoinDatabase
import com.holiware.world1.data.remote.CoinApi
import com.holiware.world1.data.repository.CoinRepositoryImpl
import com.holiware.world1.domain.repository.CoinRepository
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

    single<CoinDatabase> {
        Room.databaseBuilder(
            context = androidContext(),
            klass = CoinDatabase::class.java,
            name = "coin-database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single {
        get<CoinDatabase>().coinDao()
    }
}
