package com.github.zsoltk.pokedex.domain.usecase

import com.github.zsoltk.pokedex.domain.model.PokemonDetail
import com.github.zsoltk.pokedex.domain.repository.PokemonDetailRepository
import kotlinx.coroutines.flow.Flow

class GetFavoritesUseCase(private val pokemonDetailRepository: PokemonDetailRepository) {
    operator fun invoke(): Flow<List<PokemonDetail>> = pokemonDetailRepository.observeFavorites()
}
