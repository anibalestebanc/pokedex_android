package com.github.pokemon.pokedex.data.datasource.local

interface SyncMetaStore {
    suspend fun getLastSyncAt(syncKey: SyncKeys): Long
    suspend fun setLastSyncAt(syncKey: SyncKeys, value: Long)
}

enum class SyncKeys(val key: String) {
    LAST_SYNC_CATALOG("last_sync_catalog")
}
