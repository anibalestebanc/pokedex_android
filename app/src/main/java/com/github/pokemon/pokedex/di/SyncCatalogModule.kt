package com.github.pokemon.pokedex.di

import com.github.pokemon.pokedex.core.work.SyncCatalogWorkScheduler
import com.github.pokemon.pokedex.data.datasource.cache.CatalogCacheDataSource
import com.github.pokemon.pokedex.data.datasource.cache.RoomCatalogCacheDataSource
import com.github.pokemon.pokedex.data.datasource.remote.CatalogRemoteDataSource
import com.github.pokemon.pokedex.data.datasource.remote.RetrofitCatalogRemoteDataSource
import com.github.pokemon.pokedex.data.repository.DefaultCatalogRepository
import com.github.pokemon.pokedex.domain.repository.CatalogRepository
import com.github.pokemon.pokedex.domain.usecase.EnqueueDailySyncCatalogUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val syncCatalogModule = module {
    //CacheDatasource
    singleOf(::RoomCatalogCacheDataSource) { bind<CatalogCacheDataSource>() }

    //RemoteDataSource
    singleOf(::RetrofitCatalogRemoteDataSource) { bind<CatalogRemoteDataSource>() }

    //Repository
    singleOf(::DefaultCatalogRepository) { bind<CatalogRepository>() }

    //Scheduler
    singleOf(::SyncCatalogWorkScheduler)

    //UseCase
    factoryOf(::EnqueueDailySyncCatalogUseCase)
}
