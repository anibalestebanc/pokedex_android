package com.github.pokemon.pokedex.data.datasource.cache

import com.github.pokemon.pokedex.core.database.entity.PokemonDetailEntity
import kotlinx.coroutines.flow.Flow

interface DetailCacheDataSource {
    fun observeDetail(id: Int): Flow<PokemonDetailEntity?>
    fun observeFavorites(): Flow<List<PokemonDetailEntity>>
    fun observeIsFavorite(id: Int): Flow<Boolean>
    suspend fun getDetail(id: Int): PokemonDetailEntity?
    suspend fun insertDetail(entity: PokemonDetailEntity)
    suspend fun setFavorite(id: Int, favorite: Boolean)
}
