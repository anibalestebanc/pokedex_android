package com.github.pokemon.pokedex.di

import com.github.pokemon.pokedex.domain.usecase.DetailUseCases
import com.github.pokemon.pokedex.domain.usecase.GetFavoritesUseCase
import com.github.pokemon.pokedex.domain.usecase.GetPokemonFullDetailUseCase
import com.github.pokemon.pokedex.domain.usecase.ObserveAndRefreshDetailUseCase
import com.github.pokemon.pokedex.domain.usecase.ObserveIsFavoriteUseCase
import com.github.pokemon.pokedex.domain.usecase.SaveHistorySearchUseCase
import com.github.pokemon.pokedex.domain.usecase.SearchListUseCases
import com.github.pokemon.pokedex.domain.usecase.SearchPokemonPagedUseCase
import com.github.pokemon.pokedex.domain.usecase.ToggleFavoriteUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    //UseCase
    factoryOf(::SearchPokemonPagedUseCase)
    factoryOf(::GetPokemonFullDetailUseCase)
    factoryOf(::GetFavoritesUseCase)
    factoryOf(::ObserveIsFavoriteUseCase)
    factoryOf(::ToggleFavoriteUseCase)
    factoryOf(::ObserveAndRefreshDetailUseCase)
    factoryOf(::SaveHistorySearchUseCase)

    //Data class UseCases
    factoryOf(::DetailUseCases)
    factoryOf(::SearchListUseCases)
}
