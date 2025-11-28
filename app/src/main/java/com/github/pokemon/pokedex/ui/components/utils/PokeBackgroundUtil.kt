package com.github.pokemon.pokedex.ui.components.utils

import com.github.pokemon.pokedex.R

object PokeBackgroundUtil {
    fun primaryTypeColorRes(types: List<String>): Int {
        val t = types.firstOrNull()?.lowercase()
        return when (t) {
            "grass", "bug" -> R.color.poke_light_teal
            "fire" -> R.color.poke_light_red
            "water", "fighting", "normal" -> R.color.poke_light_blue
            "electric", "psychic" -> R.color.poke_light_yellow
            "poison", "ghost" -> R.color.poke_light_purple
            "ground", "rock" -> R.color.poke_light_brown
            "dark" -> R.color.poke_black
            else -> R. color.grey_500
        }
    }
}
