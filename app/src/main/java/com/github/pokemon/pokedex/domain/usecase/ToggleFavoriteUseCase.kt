package com.github.pokemon.pokedex.domain.usecase

import com.github.pokemon.pokedex.domain.repository.PokemonDetailRepository

class ToggleFavoriteUseCase(private val repository: PokemonDetailRepository) {
    suspend operator fun invoke(id: Int): Result<Unit> {
        return repository.getPokemonDetail(id)
            .mapCatching {
                repository.setFavorite(id, !it.isFavorite).getOrThrow()
            }.onFailure {
                return Result.failure(it)
            }
    }
}
