package com.github.pokemon.pokedex.domain.repository

import com.github.pokemon.pokedex.domain.model.PokemonDetail
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun observeIsFavorite(id: Int): Flow<Boolean>
    fun observeFavorites(): Flow<List<PokemonDetail>>
    suspend fun saveFavorite(id: Int, favorite: Boolean): Result<Unit>
    suspend fun toggleFavorite(id: Int): Result<Unit>
}
