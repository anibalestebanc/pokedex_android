package com.github.pokemon.pokedex.di

import com.github.pokemon.pokedex.ui.favorite.FavoriteViewModel
import com.github.pokemon.pokedex.ui.fullsearch.SearchFullViewModel
import com.github.pokemon.pokedex.ui.home.HomeViewModel
import com.github.pokemon.pokedex.ui.pokemondetail.PokemonDetailViewModel
import com.github.pokemon.pokedex.ui.pokemonlist.PokemonListViewModel
import com.github.pokemon.pokedex.ui.searchresult.SearchResultViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    //View models
    viewModelOf(::HomeViewModel)
    viewModelOf(::PokemonListViewModel)
    viewModelOf(::PokemonDetailViewModel)
    viewModelOf(::SearchResultViewModel)
    viewModelOf(::SearchFullViewModel)
    viewModelOf(::FavoriteViewModel)
}
