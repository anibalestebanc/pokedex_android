package com.github.pokemon.pokedex.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.pokemon.pokedex.core.database.entity.PokemonSpeciesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonSpeciesDao {
    @Query("SELECT * FROM pokemon_species WHERE id = :id LIMIT 1")
    fun observeSpecies(id: Int): Flow<PokemonSpeciesEntity?>

    @Query("SELECT * FROM pokemon_species WHERE id = :id LIMIT 1")
    suspend fun getPokemonSpecies(id: Int): PokemonSpeciesEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReplace(entity: PokemonSpeciesEntity)
}
