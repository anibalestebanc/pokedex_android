package com.github.pokemon.pokedex.di

import android.app.Application
import com.github.pokemon.pokedex.core.network.BaseUrlProvider
import com.github.pokemon.pokedex.core.network.utils.NetworkMonitor
import com.github.pokemon.pokedex.core.network.utils.OnlineNetworkMonitor
import com.github.pokemon.pokedex.core.work.SyncPokemonCatalogWorkScheduler
import com.github.pokemon.pokedex.utils.PokeTimeUtil
import com.github.pokemon.pokedex.utils.DefaultPokeTimeUtil
import com.github.pokemon.pokedex.utils.DefaultRefreshDueUtil
import com.github.pokemon.pokedex.utils.RefreshDueUtil
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.named
import org.koin.core.module.dsl.withOptions
import org.koin.dsl.module

val appModule = module {

    single<String> { BaseUrlProvider.getBaseUrl() } withOptions {
        named("default_base_url")
    }

    single { SyncPokemonCatalogWorkScheduler(androidContext()) }

    single<NetworkMonitor> {
        OnlineNetworkMonitor(
            context = get<Application>(),
        )
    }

    single<PokeTimeUtil> { DefaultPokeTimeUtil() }

    single<RefreshDueUtil> { DefaultRefreshDueUtil(get()) }
}
