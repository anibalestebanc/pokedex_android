package com.github.pokemon.pokedex.startup

interface AsyncAppInitializer {
    suspend operator fun invoke()
}
