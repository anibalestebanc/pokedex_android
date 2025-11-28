package com.github.pokemon.pokedex.utils


interface PokeTimeUtil {
    fun now(): Long
}

class DefaultPokeTimeUtil : PokeTimeUtil {
    override fun now(): Long = System.currentTimeMillis()
}
