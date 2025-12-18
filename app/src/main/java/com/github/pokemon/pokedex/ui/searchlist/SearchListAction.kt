package com.github.pokemon.pokedex.ui.searchlist

sealed interface SearchListAction {
    data class SetInitialQuery(val text: String) : SearchListAction
    data class QueryChanged(val text: String) : SearchListAction
    data object SubmitSearch : SearchListAction
}
