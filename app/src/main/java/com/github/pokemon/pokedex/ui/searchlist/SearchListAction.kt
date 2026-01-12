package com.github.pokemon.pokedex.ui.searchlist

sealed interface SearchListAction {
   data class SubmitSearch(val query: String) : SearchListAction
}
