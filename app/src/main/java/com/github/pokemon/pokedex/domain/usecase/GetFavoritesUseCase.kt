package com.github.pokemon.pokedex.domain.usecase

import com.github.pokemon.pokedex.domain.model.PokemonDetail
import com.github.pokemon.pokedex.domain.repository.PokemonDetailRepository
import kotlinx.coroutines.flow.Flow

class GetFavoritesUseCase(private val pokemonDetailRepository: PokemonDetailRepository) {
    operator fun invoke(): Flow<List<PokemonDetail>> = pokemonDetailRepository.observeFavorites()
}
