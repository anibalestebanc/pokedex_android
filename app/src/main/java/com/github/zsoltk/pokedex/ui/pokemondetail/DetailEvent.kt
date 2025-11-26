package com.github.zsoltk.pokedex.ui.pokemondetail


sealed interface DetailEvent {
    data class OnStart(val pokemonId: String) : DetailEvent
    data class OnRetryClick(val pokemonId: String) : DetailEvent
}
