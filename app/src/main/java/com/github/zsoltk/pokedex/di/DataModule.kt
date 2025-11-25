package com.github.zsoltk.pokedex.di

import androidx.room.Room
import com.github.zsoltk.pokedex.core.database.PokemonDatabase
import com.github.zsoltk.pokedex.core.network.RetrofitFactory
import com.github.zsoltk.pokedex.data.datasource.local.PokemonCatalogLocalDataSource
import com.github.zsoltk.pokedex.data.datasource.local.PrefsPokemonCatalogLocalDataSource
import com.github.zsoltk.pokedex.data.datasource.remote.PokemonCatalogRemoteDatasource
import com.github.zsoltk.pokedex.data.datasource.remote.PokemonDetailRemoteDataSource
import com.github.zsoltk.pokedex.data.datasource.remote.PokemonSpeciesRemoteDataSource
import com.github.zsoltk.pokedex.data.datasource.remote.RetrofitCatalogRemoteDataSource
import com.github.zsoltk.pokedex.data.datasource.remote.RetrofitPokemonDetailRemoteDataSource
import com.github.zsoltk.pokedex.data.datasource.remote.RetrofitPokemonSpeciesRemoteDataSource
import com.github.zsoltk.pokedex.data.datasource.remote.api.PokemonApiV2
import com.github.zsoltk.pokedex.data.repository.DefaultPokemonCatalogRepository
import com.github.zsoltk.pokedex.data.repository.OfflineFirstPokemonDetailRepository
import com.github.zsoltk.pokedex.data.repository.OfflineFirstPokemonSpeciesRepository
import com.github.zsoltk.pokedex.data.repository.RoomPokemonSearchRepository
import com.github.zsoltk.pokedex.data.repository.RoomHistorySearchRepository
import com.github.zsoltk.pokedex.domain.repository.HistorySearchRepository
import com.github.zsoltk.pokedex.domain.repository.PokemonCatalogRepository
import com.github.zsoltk.pokedex.domain.repository.PokemonDetailRepository
import com.github.zsoltk.pokedex.domain.repository.PokemonSpeciesRepository
import com.github.zsoltk.pokedex.domain.repository.SearchPokemonRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    //Database
    single {
        Room.databaseBuilder(
            context = get(),
            klass = PokemonDatabase::class.java,
            name = "pokedex.db",
        ).build()
    }

    //Dao
    single { get<PokemonDatabase>().pokemonCatalogDao() }
    single { get<PokemonDatabase>().historySearchDao() }
    single { get<PokemonDatabase>().pokemonDetailDao() }
    single { get<PokemonDatabase>().pokemonSpeciesDao() }


    //Api
    factory<PokemonApiV2> {
        RetrofitFactory.createService(
            baseUrl = get(qualifier = named("default_base_url")),
            klass = PokemonApiV2::class.java,
        )
    }
    //LocalDataSource
    singleOf(::PrefsPokemonCatalogLocalDataSource) { bind<PokemonCatalogLocalDataSource>() }

    //RemoteDataSource
    singleOf(::RetrofitCatalogRemoteDataSource) { bind<PokemonCatalogRemoteDatasource>() }
    singleOf(::RetrofitPokemonDetailRemoteDataSource) { bind<PokemonDetailRemoteDataSource>() }
    singleOf(::RetrofitPokemonSpeciesRemoteDataSource) { bind<PokemonSpeciesRemoteDataSource>() }

    //Repository
    singleOf(::DefaultPokemonCatalogRepository) { bind<PokemonCatalogRepository>() }
    singleOf(::RoomPokemonSearchRepository) { bind<SearchPokemonRepository>() }
    singleOf(::RoomHistorySearchRepository) { bind<HistorySearchRepository>() }
    singleOf(::OfflineFirstPokemonDetailRepository) { bind<PokemonDetailRepository>() }
    singleOf(::OfflineFirstPokemonSpeciesRepository) { bind<PokemonSpeciesRepository>() }

}
