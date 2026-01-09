package com.github.pokemon.pokedex.utils

@Suppress("TopLevelPropertyNaming")
const val emptyString : String = ""


fun capitalizeFirst(value: String): String =
    value.replaceFirstChar { it.uppercase() }

