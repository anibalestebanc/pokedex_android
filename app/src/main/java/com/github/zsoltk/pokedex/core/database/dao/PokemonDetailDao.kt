package com.github.zsoltk.pokedex.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.zsoltk.pokedex.core.database.entity.PokemonDetailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDetailDao {
    @Query("SELECT * FROM pokemon_detail WHERE id = :id")
    fun observePokemonDetail(id: Int): Flow<PokemonDetailEntity?>

    @Query("SELECT * FROM pokemon_detail WHERE id = :id LIMIT 1")
    suspend fun getPokemonDetail(id: Int): PokemonDetailEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReplace(entity: PokemonDetailEntity)
}
