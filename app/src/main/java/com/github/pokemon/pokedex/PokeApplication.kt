package com.github.pokemon.pokedex

import android.app.Application
import android.os.StrictMode
import com.github.pokemon.pokedex.core.di.databaseModule
import com.github.pokemon.pokedex.core.di.networkModule
import com.github.pokemon.pokedex.di.appModule
import com.github.pokemon.pokedex.di.dataModule
import com.github.pokemon.pokedex.di.domainModule
import com.github.pokemon.pokedex.di.uiModule
import com.github.pokemon.pokedex.domain.usecase.EnqueueDailySyncCatalogUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import leakcanary.LeakCanary

class PokeApplication : Application(), KoinComponent {

    private val enqueueSyncCatalogUseCase: EnqueueDailySyncCatalogUseCase by inject()

    override fun onCreate() {
        super.onCreate()

        configLeakCanary()
        configStrictMode()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@PokeApplication)
            modules(appModule, databaseModule, networkModule, dataModule, domainModule, uiModule)
        }

        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            enqueueSyncCatalogUseCase()
        }
    }

    private fun configLeakCanary() {
        if (BuildConfig.DEBUG) {
            LeakCanary.config = LeakCanary.config.copy(
                retainedVisibleThreshold = 3,
            )
        }
    }

    private fun configStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build(),
            )
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build(),
            )
        }
    }
}
