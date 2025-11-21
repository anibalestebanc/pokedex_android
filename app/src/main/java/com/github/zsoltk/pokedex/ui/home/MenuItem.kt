package com.github.zsoltk.pokedex.ui.home

import com.github.zsoltk.pokedex.R

sealed class MenuItem(val label: String, val colorResId: Int) {
    object Pokedex : MenuItem("Pokedex", R.color.poke_teal)
    object Moves : MenuItem("Moves", R.color.poke_red)
    object Abilities : MenuItem("Abilities", R.color.poke_light_blue)
    object Items : MenuItem("Items", R.color.poke_yellow)
    object Locations : MenuItem("Locations", R.color.poke_purple)
    object TypeCharts : MenuItem("Type charts", R.color.poke_brown)
}
