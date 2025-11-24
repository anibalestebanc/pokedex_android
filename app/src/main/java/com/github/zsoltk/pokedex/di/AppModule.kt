package com.github.zsoltk.pokedex.di

import com.github.zsoltk.pokedex.core.network.BaseUrlProvider
import com.github.zsoltk.pokedex.core.work.SyncPokemonCatalogWorkScheduler
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.named
import org.koin.core.module.dsl.withOptions
import org.koin.dsl.module

val appModule = module {

    single<String> { BaseUrlProvider.getBaseUrl() } withOptions {
        named("default_base_url")
    }

    single { SyncPokemonCatalogWorkScheduler(androidContext()) }
}
