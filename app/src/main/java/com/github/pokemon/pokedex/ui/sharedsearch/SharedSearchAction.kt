package com.github.pokemon.pokedex.ui.sharedsearch

sealed interface SharedSearchAction {
    data class QueryChanged(val query: String) : SharedSearchAction
    data object ClearQuery : SharedSearchAction
}
