package com.github.pokemon.pokedex.core.di

import com.github.pokemon.pokedex.core.work.SyncCatalogWorker
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.dsl.module

val workerModule = module {
    //Workers
    workerOf(::SyncCatalogWorker)
}
