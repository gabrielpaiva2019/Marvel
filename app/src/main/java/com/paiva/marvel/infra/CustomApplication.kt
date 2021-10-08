package com.paiva.marvel.infra

import android.app.Application
import com.paiva.marvel.infra.koin.apiModule
import com.paiva.marvel.infra.koin.connectionModule
import com.paiva.marvel.infra.koin.heroesModules
import com.paiva.marvel.infra.koin.serviceModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class CustomApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@CustomApplication)
            modules(listOf(heroesModules, apiModule, connectionModule, serviceModules))
        }
    }
}