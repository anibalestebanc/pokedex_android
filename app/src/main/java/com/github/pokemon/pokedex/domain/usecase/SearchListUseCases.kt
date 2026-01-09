package com.github.pokemon.pokedex.domain.usecase

data class SearchListUseCases(
    val searchPokemonPagedUseCase: SearchPokemonPagedUseCase,
    val observeAndRefreshDetailUseCase: ObserveAndRefreshDetailUseCase
)
