package com.github.pokemon.pokedex.data.datasource.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

val Context.syncDataStore: DataStore<Preferences> by preferencesDataStore(name = "sync_meta_store")

class DataStoreSyncMetaStore(private val dataStore: DataStore<Preferences>) : SyncMetaStore {

    override suspend fun getLastSyncAt(syncKey: SyncKeys): Long {
        val prefKey = longPreferencesKey(syncKey.key)
        return dataStore.data.first()[prefKey] ?: 0L
    }

    override suspend fun setLastSyncAt(syncKey: SyncKeys, value: Long) {
        val prefKey = longPreferencesKey(syncKey.key)
        dataStore.edit {
            it[prefKey] = value
        }
    }
}
