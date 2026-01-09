package com.github.pokemon.pokedex.domain.usecase

import com.github.pokemon.pokedex.domain.model.PokemonDetail
import com.github.pokemon.pokedex.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow

class GetFavoritesUseCase(private val favoriteRepository: FavoriteRepository) {
    operator fun invoke(): Flow<List<PokemonDetail>> = favoriteRepository.observeFavorites()
}
