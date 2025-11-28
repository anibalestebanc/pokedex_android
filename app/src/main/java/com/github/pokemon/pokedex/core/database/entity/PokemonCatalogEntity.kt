package com.github.pokemon.pokedex.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_catalog")
data class PokemonCatalogEntity (
    @PrimaryKey
    val id: Int,
    val name: String,
    val displayName : String,
    val url: String
)

