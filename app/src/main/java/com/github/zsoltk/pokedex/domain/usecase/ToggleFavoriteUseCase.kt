package com.github.zsoltk.pokedex.domain.usecase

import com.github.zsoltk.pokedex.domain.repository.PokemonDetailRepository

class ToggleFavoriteUseCase(private val pokemonDetailRepository: PokemonDetailRepository) {
    suspend operator fun invoke(id: Int) = pokemonDetailRepository.toggleFavorite(id)
}
