package com.github.pokemon.pokedex.ui.detail


sealed interface DetailAction {
    data class OnStart(val pokemonId: String) : DetailAction
    data class OnRetryDetailClick(val pokemonId: String) : DetailAction
    data class OnToggleFavorite(val pokemonId: String) : DetailAction
    data class OnSharePokemon(val imageUrl: String) : DetailAction
}
