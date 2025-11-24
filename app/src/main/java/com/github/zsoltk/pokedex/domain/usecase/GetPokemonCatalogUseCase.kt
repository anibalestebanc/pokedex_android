package com.github.zsoltk.pokedex.domain.usecase

import com.github.zsoltk.pokedex.domain.repository.PokemonCatalogRepository

class GetPokemonCatalogUseCase(private val repository: PokemonCatalogRepository) {
    suspend operator fun invoke() = repository.getPokemonCatalog()
}
