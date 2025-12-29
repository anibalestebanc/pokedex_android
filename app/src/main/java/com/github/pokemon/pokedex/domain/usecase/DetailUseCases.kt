package com.github.pokemon.pokedex.domain.usecase

data class DetailUseCases(
    val getFullDetailUseCase: GetPokemonFullDetailUseCase,
    val toggleFavoriteUseCase: ToggleFavoriteUseCase,
    val observeIsFavoriteUseCase: ObserveIsFavoriteUseCase,
)
