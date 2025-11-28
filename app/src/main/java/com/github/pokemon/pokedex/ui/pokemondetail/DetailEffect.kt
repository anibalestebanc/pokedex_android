package com.github.pokemon.pokedex.ui.pokemondetail

sealed interface DetailEffect {
    data class ShareUrl(val url : String) : DetailEffect
}
