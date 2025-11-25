package com.github.zsoltk.pokedex.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.github.zsoltk.pokedex.core.database.converts.PokemonSpeciesTypeConverters

@Entity(tableName = "pokemon_species")
@TypeConverters(PokemonSpeciesTypeConverters::class)
data class PokemonSpeciesEntity (
    @PrimaryKey val id: Int,
    val name: String,
    val genera: String?,
    val flavorText: String?,
    val color: String?,
    val habitat: String?,
    val eggGroups: List<String>,
    val captureRate: Int?,
    val baseHappiness: Int?,
    val growthRate: String?,
    val isLegendary: Boolean,
    val isMythical: Boolean,
    val lastUpdated: Long
)
