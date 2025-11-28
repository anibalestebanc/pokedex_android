package com.github.pokemon.pokedex.data.datasource.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class PrefsPokemonCatalogLocalDataSource(private val context: Context) : PokemonCatalogLocalDataSource {

    private val Context.syncDataStore by preferencesDataStore(name = "sync_pokemon_catalog_prefs")

    override suspend fun getLastSyncAt(): Long {
        return context.syncDataStore.data
            .map { prefs -> prefs[KEY_LAST_SYNC_AT] }
            .first() ?: 0L
    }

    override suspend fun setLastSyncAt(value: Long) {
        context.syncDataStore.edit { prefs ->
            prefs[KEY_LAST_SYNC_AT] = value
        }
    }

    private companion object {
        private val KEY_LAST_SYNC_AT = longPreferencesKey("last_sync_at")
    }
}
