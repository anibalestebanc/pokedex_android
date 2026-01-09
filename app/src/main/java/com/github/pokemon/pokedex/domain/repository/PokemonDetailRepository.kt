package com.github.pokemon.pokedex.domain.repository

import com.github.pokemon.pokedex.domain.model.PokemonDetail
import kotlinx.coroutines.flow.Flow

interface PokemonDetailRepository {
    fun observePokemonDetail(id: Int): Flow<PokemonDetail?>
    suspend fun refreshDetail(id: Int): Result<Unit>
    suspend fun getPokemonDetail(id: Int): Result<PokemonDetail>
    suspend fun getLastUpdated(id: Int): Long
}
