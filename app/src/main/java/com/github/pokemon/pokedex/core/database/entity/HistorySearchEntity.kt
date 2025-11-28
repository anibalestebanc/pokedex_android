package com.github.pokemon.pokedex.core.database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "history_search",
    indices = [Index(value = ["query"], unique = true)]
)
data class HistorySearchEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val query: String,
    val timestamp: Long
)
