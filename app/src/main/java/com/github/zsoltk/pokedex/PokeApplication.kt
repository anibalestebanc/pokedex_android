package com.github.zsoltk.pokedex

import android.app.Application
import com.github.zsoltk.pokedex.di.appModule
import com.github.zsoltk.pokedex.di.dataModule
import com.github.zsoltk.pokedex.di.domainModule
import com.github.zsoltk.pokedex.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class PokeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@PokeApplication)
            modules(appModule, dataModule, domainModule, presentationModule)
        }
    }
}
