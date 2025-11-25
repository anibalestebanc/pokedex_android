package com.github.zsoltk.pokedex.domain.repository

import com.github.zsoltk.pokedex.domain.model.PokemonDetail
import kotlinx.coroutines.flow.Flow

interface PokemonDetailRepository {
    fun observePokemonDetail(id: Int): Flow<PokemonDetail?>
    suspend fun getPokemonDetail(nameOrId: String): Result<PokemonDetail>
}
