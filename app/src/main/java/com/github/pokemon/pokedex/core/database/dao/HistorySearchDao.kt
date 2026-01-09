package com.github.pokemon.pokedex.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.pokemon.pokedex.core.database.entity.HistorySearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistorySearchDao {

    @Query("SELECT * FROM history_search ORDER BY timestamp DESC LIMIT :limit")
    fun getLastSearch(limit: Int = 10): Flow<List<HistorySearchEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIgnore(entity: HistorySearchEntity): Long

    @Query("UPDATE history_search SET timestamp = :newTs WHERE query = :query")
    suspend fun updateTimestamp(query: String, newTs: Long)

    @Query("SELECT * FROM history_search  WHERE query = :query LIMIT 1")
    suspend fun getQuery(query: String): HistorySearchEntity?

    @Query("DELETE FROM history_search WHERE query = :query")
    suspend fun deleteByQuery(query: String)

    @Query("DELETE FROM history_search")
    suspend fun clearAll()
}
