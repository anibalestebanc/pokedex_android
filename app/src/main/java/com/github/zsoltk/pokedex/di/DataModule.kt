package com.github.zsoltk.pokedex.di

import androidx.room.Room
import com.github.zsoltk.pokedex.core.database.PokemonDatabase
import com.github.zsoltk.pokedex.core.network.RetrofitFactory
import com.github.zsoltk.pokedex.data.datasource.local.PokemonCatalogLocalDataSource
import com.github.zsoltk.pokedex.data.datasource.local.PrefsPokemonCatalogLocalDataSource
import com.github.zsoltk.pokedex.data.datasource.remote.PokemonCatalogRemoteDatasource
import com.github.zsoltk.pokedex.data.datasource.remote.RetrofitCatalogRemoteDataSource
import com.github.zsoltk.pokedex.data.datasource.remote.api.PokemonApiV2
import com.github.zsoltk.pokedex.data.repository.DefaultPokemonCatalogRepository
import com.github.zsoltk.pokedex.data.repository.OfflinePokemonSearchRepository
import com.github.zsoltk.pokedex.data.repository.RoomHistorySearchRepository
import com.github.zsoltk.pokedex.domain.repository.HistorySearchRepository
import com.github.zsoltk.pokedex.domain.repository.PokemonCatalogRepository
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

    //Repository
    singleOf(::DefaultPokemonCatalogRepository) { bind<PokemonCatalogRepository>() }
    singleOf(::OfflinePokemonSearchRepository) { bind<SearchPokemonRepository>() }
    singleOf(::RoomHistorySearchRepository) { bind<HistorySearchRepository>() }

}
