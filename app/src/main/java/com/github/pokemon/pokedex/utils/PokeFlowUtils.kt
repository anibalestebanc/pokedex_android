package com.github.pokemon.pokedex.utils

import androidx.lifecycle.SavedStateHandle

fun <T> SavedStateHandle.getAndConsume(key: String): T? {
    val v = get<T>(key)
    if (v != null) remove<T>(key)
    return v
}
