package com.github.pokemon.pokedex

import android.app.Application
import androidx.work.Configuration
import com.github.pokemon.pokedex.core.di.databaseModule
import com.github.pokemon.pokedex.core.di.networkModule
import com.github.pokemon.pokedex.core.di.workerModule
import com.github.pokemon.pokedex.di.appModule
import com.github.pokemon.pokedex.di.dataModule
import com.github.pokemon.pokedex.di.domainModule
import com.github.pokemon.pokedex.di.startupModule
import com.github.pokemon.pokedex.di.syncCatalogModule
import com.github.pokemon.pokedex.di.uiModule
import com.github.pokemon.pokedex.startup.AppInitializer
import com.github.pokemon.pokedex.startup.StrictModeInitializer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.android.ext.android.getKoin
import org.koin.androidx.workmanager.factory.KoinWorkerFactory
import org.koin.androidx.workmanager.koin.workManagerFactory
import kotlin.collections.List

class PokeApplication : Application(), Configuration.Provider  {

    override fun onCreate() {
        super.onCreate()

        val initializers: List<AppInitializer> = listOf(
            StrictModeInitializer(),
        )
        initializers.forEach { initializer -> initializer() }

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
                startupModule,
                dataModule,
                domainModule,
                uiModule,
            )
        }

        val asyncInitializers : List<AppInitializer> = getKoin().getAll()
        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            asyncInitializers.forEach { initializer ->
                initializer()
            }
        }
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(KoinWorkerFactory())
            .build()

}
