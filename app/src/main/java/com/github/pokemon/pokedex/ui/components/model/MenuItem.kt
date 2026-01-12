package com.github.pokemon.pokedex.ui.components.model

import androidx.compose.runtime.Immutable
import com.github.pokemon.pokedex.R

val HomeOptions = listOf(
    MenuItem.Pokedex,
    MenuItem.Moves,
    MenuItem.Abilities,
    MenuItem.Items,
    MenuItem.Locations,
    MenuItem.TypeCharts,
)

@Immutable
sealed class MenuItem(val label: Int, val colorResId: Int) {
    object Pokedex : MenuItem(R.string.menu_pokedex, R.color.poke_teal)
    object Moves : MenuItem(R.string.menu_moves, R.color.poke_red)
    object Abilities : MenuItem(R.string.menu_abilities, R.color.poke_light_blue)
    object Items : MenuItem(R.string.menu_items, R.color.poke_yellow)
    object Locations : MenuItem(R.string.menu_locations, R.color.poke_purple)
    object TypeCharts : MenuItem(R.string.menu_type_charts, R.color.poke_brown)
}
