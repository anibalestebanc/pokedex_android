package com.github.pokemon.pokedex.core.di

import androidx.room.Room
import com.github.pokemon.pokedex.core.database.PokemonDatabase
import org.koin.dsl.module

val databaseModule = module {

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
}
