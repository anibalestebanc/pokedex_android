package com.github.pokemon.pokedex.ui.home.model

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import com.github.pokemon.pokedex.R

sealed class MenuItem(@StringRes val label: Int, @ColorRes val colorResId: Int) {
    object Pokedex : MenuItem(R.string.menu_pokedex, R.color.poke_teal)
    object Moves : MenuItem(R.string.menu_moves, R.color.poke_red)
    object Abilities : MenuItem(R.string.menu_abilities, R.color.poke_light_blue)
    object Items : MenuItem(R.string.menu_items, R.color.poke_yellow)
    object Locations : MenuItem(R.string.menu_locations, R.color.poke_purple)
    object TypeCharts : MenuItem(R.string.menu_type_charts, R.color.poke_brown)
}
