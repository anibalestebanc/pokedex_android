package com.github.zsoltk.pokedex.ui.pokemondetail


sealed interface DetailEvent {
    data class OnStart(val idOrName: String) : DetailEvent
    data class OnRetryClick(val idOrName: String) : DetailEvent
}
