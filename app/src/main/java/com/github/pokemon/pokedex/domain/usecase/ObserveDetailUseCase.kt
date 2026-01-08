package com.github.pokemon.pokedex.domain.usecase

import com.github.pokemon.pokedex.domain.model.PokemonDetail
import com.github.pokemon.pokedex.domain.repository.PokemonDetailRepository
import kotlinx.coroutines.flow.Flow

class ObserveDetailUseCase(private val repository: PokemonDetailRepository) {
    operator fun invoke(id: Int): Flow<PokemonDetail?> = repository.observePokemonDetail(id)
}
