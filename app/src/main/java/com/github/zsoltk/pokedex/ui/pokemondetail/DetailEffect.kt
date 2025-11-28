package com.github.zsoltk.pokedex.ui.pokemondetail

sealed interface DetailEffect {
    data class ShareUrl(val url : String) : DetailEffect
}
