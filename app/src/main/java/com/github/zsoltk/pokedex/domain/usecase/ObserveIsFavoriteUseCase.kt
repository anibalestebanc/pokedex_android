package com.github.zsoltk.pokedex.domain.usecase

import com.github.zsoltk.pokedex.domain.repository.PokemonDetailRepository
import kotlinx.coroutines.flow.Flow

class ObserveIsFavoriteUseCase(private val pokemonDetailRepository: PokemonDetailRepository) {
    operator fun invoke(id: Int): Flow<Boolean> = pokemonDetailRepository.observeIsFavorite(id)
}
