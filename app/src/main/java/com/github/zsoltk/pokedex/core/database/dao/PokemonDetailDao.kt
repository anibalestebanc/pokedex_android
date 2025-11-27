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

    @Query("UPDATE pokemon_detail SET isFavorite = :favorite WHERE id = :id")
    suspend fun setFavorite(id: Int, favorite: Boolean)

    @Query("SELECT EXISTS(SELECT 1 FROM pokemon_detail WHERE id = :id AND isFavorite = 1)")
    fun observeIsFavorite(id: Int): Flow<Boolean>
    @Query("SELECT * FROM pokemon_detail WHERE isFavorite = 1 ORDER BY name COLLATE NOCASE ASC")
    fun observeFavorites(): Flow<List<PokemonDetailEntity>>

    @Query("SELECT * FROM pokemon_detail WHERE isFavorite = 1 ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun getFavoritesPage(limit: Int, offset: Int): List<PokemonDetailEntity>
}
