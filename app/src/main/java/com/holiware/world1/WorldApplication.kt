package com.holiware.world1

import android.app.Application
import com.holiware.world1.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WorldApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(appModules)
        }
    }
}