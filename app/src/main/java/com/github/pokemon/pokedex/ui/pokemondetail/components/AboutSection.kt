package com.github.pokemon.pokedex.ui.pokemondetail.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.github.pokemon.pokedex.domain.model.Pokemon

@Composable
fun AboutSection(pokemon: Pokemon) {
    pokemon.description?.let {
        Text(it)
    }
}
