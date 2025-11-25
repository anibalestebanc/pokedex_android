package com.github.zsoltk.pokedex.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.github.zsoltk.pokedex.core.database.converts.PokemonDetailTypeConverters
import com.github.zsoltk.pokedex.domain.model.Stat

@Entity(tableName = "pokemon_detail")
@TypeConverters(PokemonDetailTypeConverters::class)
data class PokemonDetailEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val imageUrl: String?,
    val types: List<String>,
    val height: Int,
    val weight: Int,
    val abilities: List<String>,
    val stats: List<Stat>,
    val lastUpdated: Long,
)
