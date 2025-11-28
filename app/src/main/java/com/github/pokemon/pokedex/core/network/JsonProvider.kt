package com.github.pokemon.pokedex.core.network

import kotlinx.serialization.json.Json

object JsonProvider {

    val json by lazy {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
            coerceInputValues = true
        }
    }
}
