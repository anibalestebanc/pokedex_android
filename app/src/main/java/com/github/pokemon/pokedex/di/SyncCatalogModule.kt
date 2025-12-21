package com.github.pokemon.pokedex.di

import com.github.pokemon.pokedex.core.work.SyncCatalogWorkScheduler
import com.github.pokemon.pokedex.data.datasource.cache.PokemonCatalogCacheDataSource
import com.github.pokemon.pokedex.data.datasource.cache.RoomCatalogCacheDataSource
import com.github.pokemon.pokedex.data.datasource.local.PrefsSyncCatalogLocalDataSource
import com.github.pokemon.pokedex.data.datasource.local.SyncCatalogLocalDataSource
import com.github.pokemon.pokedex.data.datasource.remote.PokemonCatalogRemoteDataSource
import com.github.pokemon.pokedex.data.datasource.remote.RetrofitCatalogRemoteDataSource
import com.github.pokemon.pokedex.data.repository.DefaultCatalogRepository
import com.github.pokemon.pokedex.data.repository.LocalSyncCatalogRepository
import com.github.pokemon.pokedex.domain.repository.CatalogRepository
import com.github.pokemon.pokedex.domain.repository.SyncCatalogRepository
import com.github.pokemon.pokedex.domain.usecase.EnqueueDailySyncCatalogUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val syncCatalogModule = module {
    //CacheDatasource
    singleOf(::RoomCatalogCacheDataSource) { bind<PokemonCatalogCacheDataSource>() }

    //RemoteDataSource
    singleOf(::RetrofitCatalogRemoteDataSource) { bind<PokemonCatalogRemoteDataSource>() }

    //LocalDataSource
    singleOf(::PrefsSyncCatalogLocalDataSource) { bind<SyncCatalogLocalDataSource>() }

    //Repository
    singleOf(::LocalSyncCatalogRepository){ bind<SyncCatalogRepository>()}
    singleOf(::DefaultCatalogRepository){ bind<CatalogRepository>()}

    //Scheduler
    singleOf(::SyncCatalogWorkScheduler)

    //UseCase
    factoryOf(::EnqueueDailySyncCatalogUseCase)
}
