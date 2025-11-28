package com.github.zsoltk.pokedex

import android.app.Application
import com.github.zsoltk.pokedex.di.appModule
import com.github.zsoltk.pokedex.di.dataModule
import com.github.zsoltk.pokedex.di.domainModule
import com.github.zsoltk.pokedex.di.presentationModule
import com.github.zsoltk.pokedex.domain.usecase.EnqueueDailySyncCatalogUseCase
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

class PokeApplication : Application(), KoinComponent {

    private val enqueueSyncCatalogUseCase: EnqueueDailySyncCatalogUseCase by inject()

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@PokeApplication)
            modules(appModule, dataModule, domainModule, presentationModule)
        }
        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            enqueueSyncCatalogUseCase()
        }
    }
}
