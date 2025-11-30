package com.github.pokemon.pokedex.data.datasource.cache

import androidx.room.withTransaction
import com.github.pokemon.pokedex.core.database.PokemonDatabase
import com.github.pokemon.pokedex.core.database.entity.PokemonCatalogEntity

class RoomCatalogCacheDataSource(private val database: PokemonDatabase) : PokemonCatalogCacheDataSource {
    override suspend fun clearAndInsertAllCatalog(items: List<PokemonCatalogEntity>) {
        database.withTransaction {
            val dao = database.pokemonCatalogDao()
            dao.clear()
            dao.insertAll(items)
        }
    }
}
