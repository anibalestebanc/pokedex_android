package com.github.pokemon.pokedex

import android.app.Application
import android.os.StrictMode
import androidx.work.Configuration
import com.github.pokemon.pokedex.core.di.databaseModule
import com.github.pokemon.pokedex.core.di.networkModule
import com.github.pokemon.pokedex.core.di.workerModule
import com.github.pokemon.pokedex.di.appModule
import com.github.pokemon.pokedex.di.dataModule
import com.github.pokemon.pokedex.di.domainModule
import com.github.pokemon.pokedex.di.syncCatalogModule
import com.github.pokemon.pokedex.di.uiModule
import com.github.pokemon.pokedex.domain.usecase.EnqueueDailySyncCatalogUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import leakcanary.LeakCanary
import org.koin.android.ext.android.getKoin
import org.koin.androidx.workmanager.factory.KoinWorkerFactory
import org.koin.androidx.workmanager.koin.workManagerFactory

class PokeApplication : Application(), Configuration.Provider {

    override fun onCreate() {
        super.onCreate()

        configLeakCanary()

        configStrictMode()

        startKoin {
            androidContext(this@PokeApplication)
            androidLogger(Level.ERROR)
            workManagerFactory()
            modules(
                appModule,
                databaseModule,
                networkModule,
                syncCatalogModule,
                workerModule,
                dataModule,
                domainModule,
                uiModule,
            )
        }

        val enqueueSyncCatalogUseCase: EnqueueDailySyncCatalogUseCase =
            getKoin().get()

        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            enqueueSyncCatalogUseCase()
        }
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(KoinWorkerFactory())
            .build()

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
