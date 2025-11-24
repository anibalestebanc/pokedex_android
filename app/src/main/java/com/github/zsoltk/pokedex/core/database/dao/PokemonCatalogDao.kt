package com.github.zsoltk.pokedex.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.zsoltk.pokedex.core.database.entity.PokemonCatalogEntity

@Dao
interface PokemonCatalogDao {

    @Query("SELECT COUNT(*) FROM pokemon_catalog")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<PokemonCatalogEntity>)

    @Query("DELETE FROM pokemon_catalog")
    suspend fun clear()

    @Query("SELECT * FROM pokemon_catalog ORDER BY id ASC")
    fun pagingSourceAll(): PagingSource<Int, PokemonCatalogEntity>

    @Query("""
        SELECT * FROM pokemon_catalog
        WHERE name LIKE '%' || :query || '%'
        ORDER BY id ASC
    """)
    fun pagingSourceByQuery(query: String): PagingSource<Int, PokemonCatalogEntity>
}
