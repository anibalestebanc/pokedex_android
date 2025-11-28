package com.github.pokemon.pokedex.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.pokemon.pokedex.core.database.converts.PokemonDetailTypeConverters
import com.github.pokemon.pokedex.core.database.dao.HistorySearchDao
import com.github.pokemon.pokedex.core.database.dao.PokemonCatalogDao
import com.github.pokemon.pokedex.core.database.dao.PokemonDetailDao
import com.github.pokemon.pokedex.core.database.dao.PokemonSpeciesDao
import com.github.pokemon.pokedex.core.database.entity.PokemonCatalogEntity
import com.github.pokemon.pokedex.core.database.entity.HistorySearchEntity
import com.github.pokemon.pokedex.core.database.entity.PokemonDetailEntity
import com.github.pokemon.pokedex.core.database.entity.PokemonSpeciesEntity

@Database(
    entities = [
        PokemonCatalogEntity::class,
        HistorySearchEntity::class,
        PokemonDetailEntity::class,
        PokemonSpeciesEntity::class
    ],
    version = 1,
    exportSchema = false,
)
@TypeConverters(PokemonDetailTypeConverters::class)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonCatalogDao(): PokemonCatalogDao
    abstract fun historySearchDao(): HistorySearchDao
    abstract fun pokemonDetailDao(): PokemonDetailDao
    abstract fun pokemonSpeciesDao(): PokemonSpeciesDao
}
