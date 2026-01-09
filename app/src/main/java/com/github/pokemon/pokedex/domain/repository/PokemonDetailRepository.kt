package com.github.pokemon.pokedex.domain.repository

import com.github.pokemon.pokedex.domain.model.PokemonDetail
import kotlinx.coroutines.flow.Flow

interface PokemonDetailRepository {
    fun observePokemonDetail(id: Int): Flow<PokemonDetail?>
    suspend fun getPokemonDetail(id: Int): Result<PokemonDetail>
}
