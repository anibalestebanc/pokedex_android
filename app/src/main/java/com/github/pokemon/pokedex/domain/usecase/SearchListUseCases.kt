package com.github.pokemon.pokedex.domain.usecase

data class SearchListUseCases(
    val searchUseCase: SearchPokemonUseCase,
    val observeDetailUseCase: ObserveDetailUseCase
)
