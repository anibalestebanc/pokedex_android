package com.github.pokemon.pokedex.domain.usecase

import com.github.pokemon.pokedex.domain.repository.FavoriteRepository

class ToggleFavoriteUseCase(private val favoriteRepository: FavoriteRepository) {
    suspend operator fun invoke(id: Int): Result<Unit> = favoriteRepository.toggleFavorite(id)
}
