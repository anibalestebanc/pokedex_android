package com.github.pokemon.pokedex.di

import com.github.pokemon.pokedex.data.datasource.cache.DetailCacheDataSource
import com.github.pokemon.pokedex.data.datasource.cache.HistorySearchCacheDataSource
import com.github.pokemon.pokedex.data.datasource.cache.RoomDetailCacheDataSource
import com.github.pokemon.pokedex.data.datasource.cache.RoomHistorySearchCacheDataSource
import com.github.pokemon.pokedex.data.datasource.cache.RoomSearchCacheDatasource
import com.github.pokemon.pokedex.data.datasource.cache.RoomSpeciesCacheDataSource
import com.github.pokemon.pokedex.data.datasource.cache.SearchCacheDatasource
import com.github.pokemon.pokedex.data.datasource.cache.SpeciesCacheDataSource
import com.github.pokemon.pokedex.data.datasource.remote.PokemonDetailRemoteDataSource
import com.github.pokemon.pokedex.data.datasource.remote.PokemonSpeciesRemoteDataSource
import com.github.pokemon.pokedex.data.datasource.remote.RetrofitPokemonDetailRemoteDataSource
import com.github.pokemon.pokedex.data.datasource.remote.RetrofitPokemonSpeciesRemoteDataSource
import com.github.pokemon.pokedex.data.repository.OfflineFirstPokemonDetailRepository
import com.github.pokemon.pokedex.data.repository.OfflineFirstPokemonSpeciesRepository
import com.github.pokemon.pokedex.data.repository.RoomHistorySearchRepository
import com.github.pokemon.pokedex.data.repository.RoomPokemonSearchRepository
import com.github.pokemon.pokedex.domain.repository.HistorySearchRepository
import com.github.pokemon.pokedex.domain.repository.PokemonDetailRepository
import com.github.pokemon.pokedex.domain.repository.PokemonSpeciesRepository
import com.github.pokemon.pokedex.domain.repository.SearchPokemonRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    //CacheDatasource
    singleOf(::RoomHistorySearchCacheDataSource) { bind<HistorySearchCacheDataSource>() }
    singleOf(::RoomSpeciesCacheDataSource) { bind<SpeciesCacheDataSource>() }
    singleOf(::RoomSearchCacheDatasource) { bind<SearchCacheDatasource>() }
    singleOf(::RoomDetailCacheDataSource) { bind<DetailCacheDataSource>() }

    //RemoteDataSource
    singleOf(::RetrofitPokemonDetailRemoteDataSource) { bind<PokemonDetailRemoteDataSource>() }
    singleOf(::RetrofitPokemonSpeciesRemoteDataSource) { bind<PokemonSpeciesRemoteDataSource>() }

    //Repository
    singleOf(::RoomPokemonSearchRepository) { bind<SearchPokemonRepository>() }
    singleOf(::RoomHistorySearchRepository) { bind<HistorySearchRepository>() }
    singleOf(::OfflineFirstPokemonDetailRepository) { bind<PokemonDetailRepository>() }
    singleOf(::OfflineFirstPokemonSpeciesRepository) { bind<PokemonSpeciesRepository>() }
}
