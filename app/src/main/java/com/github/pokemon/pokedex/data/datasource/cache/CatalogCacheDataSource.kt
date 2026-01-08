package com.github.pokemon.pokedex.data.datasource.cache

import androidx.paging.PagingSource
import com.github.pokemon.pokedex.core.database.entity.PokemonCatalogEntity

interface CatalogCacheDataSource {
    suspend fun clearAndInsertAllCatalog(items: List<PokemonCatalogEntity>)
    fun searchAllPaged(): PagingSource<Int, PokemonCatalogEntity>
    fun searchByNamePaged(query: String): PagingSource<Int, PokemonCatalogEntity>
}
