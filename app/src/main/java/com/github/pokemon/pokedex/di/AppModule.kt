package com.github.pokemon.pokedex.di

import android.app.Application
import com.github.pokemon.pokedex.core.common.loggin.DefaultLoggerError
import com.github.pokemon.pokedex.core.common.loggin.LoggerError
import com.github.pokemon.pokedex.core.network.BaseUrlProvider
import com.github.pokemon.pokedex.core.network.utils.NetworkMonitor
import com.github.pokemon.pokedex.core.network.utils.OnlineNetworkMonitor
import com.github.pokemon.pokedex.core.work.SyncPokemonCatalogWorkScheduler
import com.github.pokemon.pokedex.utils.PokeTimeUtil
import com.github.pokemon.pokedex.utils.DefaultPokeTimeUtil
import com.github.pokemon.pokedex.utils.DefaultRefreshDueUtil
import com.github.pokemon.pokedex.utils.RefreshDueUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.named
import org.koin.core.module.dsl.withOptions
import org.koin.dsl.module

val appModule = module {

    single<String> { BaseUrlProvider.getBaseUrl() } withOptions {
        named("default_base_url")
    }

    single<CoroutineDispatcher> { Dispatchers.IO } withOptions {
        named("io_dispatcher")
    }

    single { SyncPokemonCatalogWorkScheduler(androidContext()) }

    single<NetworkMonitor> {
        OnlineNetworkMonitor(
            context = get<Application>(),
        )
    }
    single<LoggerError> { DefaultLoggerError() }

    single<PokeTimeUtil> { DefaultPokeTimeUtil() }

    single<RefreshDueUtil> { DefaultRefreshDueUtil(get()) }
}
