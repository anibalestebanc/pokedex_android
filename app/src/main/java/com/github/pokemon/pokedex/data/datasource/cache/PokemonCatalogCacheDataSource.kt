package com.github.pokemon.pokedex.data.datasource.cache

import com.github.pokemon.pokedex.core.database.entity.PokemonCatalogEntity

interface PokemonCatalogCacheDataSource {
    suspend fun clearAndInsertAllCatalog(items: List<PokemonCatalogEntity>)
}
