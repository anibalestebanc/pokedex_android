package com.github.pokemon.pokedex.domain.usecase

import com.github.pokemon.pokedex.domain.repository.PokemonDetailRepository
import kotlinx.coroutines.flow.Flow

class ObserveIsFavoriteUseCase(private val repository: PokemonDetailRepository) {
    operator fun invoke(id: Int): Flow<Boolean> = repository.observeIsFavorite(id)
}
