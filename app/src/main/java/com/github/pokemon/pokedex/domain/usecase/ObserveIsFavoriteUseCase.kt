package com.github.pokemon.pokedex.domain.usecase

import com.github.pokemon.pokedex.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow

class ObserveIsFavoriteUseCase(private val favoriteRepository: FavoriteRepository) {
    operator fun invoke(id: Int): Flow<Boolean> = favoriteRepository.observeIsFavorite(id)
}
