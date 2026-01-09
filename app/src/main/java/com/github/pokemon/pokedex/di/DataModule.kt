package com.github.pokemon.pokedex.di

import com.github.pokemon.pokedex.data.datasource.cache.DetailCacheDataSource
import com.github.pokemon.pokedex.data.datasource.cache.HistorySearchCacheDataSource
import com.github.pokemon.pokedex.data.datasource.cache.RoomDetailCacheDataSource
import com.github.pokemon.pokedex.data.datasource.cache.RoomHistorySearchCacheDataSource
import com.github.pokemon.pokedex.data.datasource.cache.RoomSpeciesCacheDataSource
import com.github.pokemon.pokedex.data.datasource.cache.SpeciesCacheDataSource
import com.github.pokemon.pokedex.data.datasource.remote.DetailRemoteDataSource
import com.github.pokemon.pokedex.data.datasource.remote.SpeciesRemoteDataSource
import com.github.pokemon.pokedex.data.datasource.remote.RetrofitDetailRemoteDataSource
import com.github.pokemon.pokedex.data.datasource.remote.RetrofitSpeciesRemoteDataSource
import com.github.pokemon.pokedex.data.repository.DefaultFavoriteRepository
import com.github.pokemon.pokedex.data.repository.OfflineFirstDetailRepository
import com.github.pokemon.pokedex.data.repository.OfflineFirstSpeciesRepository
import com.github.pokemon.pokedex.data.repository.RoomHistorySearchRepository
import com.github.pokemon.pokedex.domain.repository.FavoriteRepository
import com.github.pokemon.pokedex.domain.repository.HistorySearchRepository
import com.github.pokemon.pokedex.domain.repository.PokemonDetailRepository
import com.github.pokemon.pokedex.domain.repository.PokemonSpeciesRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    //CacheDatasource
    singleOf(::RoomHistorySearchCacheDataSource) { bind<HistorySearchCacheDataSource>() }
    singleOf(::RoomSpeciesCacheDataSource) { bind<SpeciesCacheDataSource>() }
    singleOf(::RoomDetailCacheDataSource) { bind<DetailCacheDataSource>() }

    //RemoteDataSource
    singleOf(::RetrofitDetailRemoteDataSource) { bind<DetailRemoteDataSource>() }
    singleOf(::RetrofitSpeciesRemoteDataSource) { bind<SpeciesRemoteDataSource>() }

    //Repository
    singleOf(::RoomHistorySearchRepository) { bind<HistorySearchRepository>() }
    singleOf(::OfflineFirstDetailRepository) { bind<PokemonDetailRepository>() }
    singleOf(::OfflineFirstSpeciesRepository) { bind<PokemonSpeciesRepository>() }
    singleOf(::DefaultFavoriteRepository) { bind<FavoriteRepository>() }
}
