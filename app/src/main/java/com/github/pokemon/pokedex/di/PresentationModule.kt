package com.github.pokemon.pokedex.di

import com.github.pokemon.pokedex.ui.favorite.FavoriteViewModel
import com.github.pokemon.pokedex.ui.search.SearchViewModel
import com.github.pokemon.pokedex.ui.home.HomeViewModel
import com.github.pokemon.pokedex.ui.detail.DetailViewModel
import com.github.pokemon.pokedex.ui.searchresult.SearchResultViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    //View models
    viewModelOf(::HomeViewModel)
    viewModelOf(::DetailViewModel)
    viewModelOf(::SearchResultViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::FavoriteViewModel)
}
