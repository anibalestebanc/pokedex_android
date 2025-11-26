package com.github.zsoltk.pokedex.di

import com.github.zsoltk.pokedex.ui.fullsearch.SearchFullViewModel
import com.github.zsoltk.pokedex.ui.home.HomeViewModel
import com.github.zsoltk.pokedex.ui.pokemondetail.PokemonDetailViewModel
import com.github.zsoltk.pokedex.ui.pokemonlist.PokemonListViewModel
import com.github.zsoltk.pokedex.ui.searchresult.SearchResultViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    //View models
    viewModelOf(::HomeViewModel)
    viewModelOf(::PokemonListViewModel)
    viewModelOf(::PokemonDetailViewModel)
    viewModelOf(::SearchResultViewModel)
    viewModelOf(::SearchFullViewModel)
}
