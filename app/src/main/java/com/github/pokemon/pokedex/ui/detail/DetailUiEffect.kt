package com.github.pokemon.pokedex.ui.detail

sealed interface DetailUiEffect {
    data class ShareUrl(val url : String) : DetailUiEffect
}
