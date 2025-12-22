package com.github.pokemon.pokedex.di

import com.github.pokemon.pokedex.startup.AsyncAppInitializer
import com.github.pokemon.pokedex.startup.SyncCatalogInitializer
import org.koin.core.qualifier.named
import org.koin.dsl.module

val startupModule = module {
    single<AsyncAppInitializer>(named("sync_catalog_initializer")) {
        SyncCatalogInitializer(get())
    }
}
