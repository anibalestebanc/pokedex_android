package com.github.zsoltk.pokedex.domain.repository

import com.github.zsoltk.pokedex.domain.model.PokemonDetail
import kotlinx.coroutines.flow.Flow

interface PokemonDetailRepository {
    fun observePokemonDetail(id: Int): Flow<PokemonDetail?>

    fun observeFavorites(): Flow<List<PokemonDetail>>

    fun observeIsFavorite(id: Int): Flow<Boolean>
    suspend fun getPokemonDetail(nameOrId: String): Result<PokemonDetail>

    suspend fun setFavorite(id: Int, favorite: Boolean) : Result<Unit>

    suspend fun toggleFavorite(id: Int) : Result<Boolean>
}
