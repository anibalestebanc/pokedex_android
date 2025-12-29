package com.github.pokemon.pokedex.domain.usecase

import com.github.pokemon.pokedex.domain.repository.PokemonDetailRepository

class ToggleFavoriteUseCase(private val repository: PokemonDetailRepository) {
    suspend operator fun invoke(id: Int): Result<Unit> = repository.toggleFavorite(id)
}
