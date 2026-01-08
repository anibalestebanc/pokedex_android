package com.github.pokemon.pokedex.domain.model

sealed interface SearchQuery {
    data object All : SearchQuery
    data class ByName(val query: String) : SearchQuery
}
