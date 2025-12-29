package com.github.pokemon.pokedex.domain.usecase

import com.github.pokemon.pokedex.domain.repository.PokemonDetailRepository

class ObserveDetailUseCase(private val repository: PokemonDetailRepository) {
    operator fun invoke(id: Int) = repository.observePokemonDetail(id)
}
