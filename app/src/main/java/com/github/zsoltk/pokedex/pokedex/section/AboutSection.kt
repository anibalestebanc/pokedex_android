package com.github.zsoltk.pokedex.pokedex.section

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.github.zsoltk.pokedex.entity.Pokemon

@Composable
fun AboutSection(pokemon: Pokemon) {
    pokemon.description?.let {
        Text(it)
    }
}
