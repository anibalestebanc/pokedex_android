package com.github.zsoltk.pokedex.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.zsoltk.pokedex.core.database.dao.PokemonCatalogDao
import com.github.zsoltk.pokedex.core.database.entity.PokemonCatalogEntity

@Database(
    entities = [PokemonCatalogEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonCatalogDao(): PokemonCatalogDao
}
