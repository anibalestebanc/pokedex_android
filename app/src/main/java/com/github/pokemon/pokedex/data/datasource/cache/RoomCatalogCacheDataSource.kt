package com.github.pokemon.pokedex.data.datasource.cache

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.github.pokemon.pokedex.core.database.PokemonDatabase
import com.github.pokemon.pokedex.core.database.entity.PokemonCatalogEntity

class RoomCatalogCacheDataSource(private val database: PokemonDatabase) : CatalogCacheDataSource {
    private val catalogDao = database.pokemonCatalogDao()

    override suspend fun clearAndInsertAllCatalog(items: List<PokemonCatalogEntity>) {
        database.withTransaction {
            val dao = database.pokemonCatalogDao()
            dao.clear()
            dao.insertAll(items)
        }
    }

    override fun searchAllPaged(): PagingSource<Int, PokemonCatalogEntity> =
        catalogDao.pagingSourceAll()

    override fun searchByNamePaged(query: String): PagingSource<Int, PokemonCatalogEntity> =
        catalogDao.pagingSourceByQuery(query)

}
