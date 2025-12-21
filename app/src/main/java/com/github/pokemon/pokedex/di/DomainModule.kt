package com.github.pokemon.pokedex.di

import com.github.pokemon.pokedex.domain.usecase.GetFavoritesUseCase
import com.github.pokemon.pokedex.domain.usecase.GetPokemonFullDetailUseCase
import com.github.pokemon.pokedex.domain.usecase.ObserveIsFavoriteUseCase
import com.github.pokemon.pokedex.domain.usecase.SearchPokemonUseCase
import com.github.pokemon.pokedex.domain.usecase.ToggleFavoriteUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    //UseCase
    factoryOf(::SearchPokemonUseCase)
    factoryOf(::GetPokemonFullDetailUseCase)
    factoryOf(::GetFavoritesUseCase)
    factoryOf(::ObserveIsFavoriteUseCase)
    factoryOf(::ToggleFavoriteUseCase)
}
