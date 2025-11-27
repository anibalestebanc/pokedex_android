package com.github.zsoltk.pokedex.di

import com.github.zsoltk.pokedex.domain.usecase.EnqueueDailySyncCatalogUseCase
import com.github.zsoltk.pokedex.domain.usecase.GetFavoritesUseCase
import com.github.zsoltk.pokedex.domain.usecase.GetPokemonFullDetailUseCase
import com.github.zsoltk.pokedex.domain.usecase.ObserveIsFavoriteUseCase
import com.github.zsoltk.pokedex.domain.usecase.SearchPokemonUseCase
import com.github.zsoltk.pokedex.domain.usecase.ToggleFavoriteUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    //UseCase
    factoryOf(::EnqueueDailySyncCatalogUseCase)
    factoryOf(::SearchPokemonUseCase)
    factoryOf(::GetPokemonFullDetailUseCase)
    factoryOf(::GetFavoritesUseCase)
    factoryOf(::ObserveIsFavoriteUseCase)
    factoryOf(::ToggleFavoriteUseCase)
}
