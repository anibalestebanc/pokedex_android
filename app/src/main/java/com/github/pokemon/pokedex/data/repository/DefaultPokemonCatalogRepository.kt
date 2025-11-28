package com.github.pokemon.pokedex.data.repository

import androidx.room.withTransaction
import com.github.pokemon.pokedex.core.common.loggin.LoggerError
import com.github.pokemon.pokedex.core.database.PokemonDatabase
import com.github.pokemon.pokedex.data.datasource.local.PokemonCatalogLocalDataSource
import com.github.pokemon.pokedex.data.mapper.toEntity
import com.github.pokemon.pokedex.data.datasource.remote.PokemonCatalogRemoteDatasource
import com.github.pokemon.pokedex.domain.repository.PokemonCatalogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultPokemonCatalogRepository(
    private val remoteDataSource: PokemonCatalogRemoteDatasource,
    private val localDataSource: PokemonCatalogLocalDataSource,
    private val database: PokemonDatabase,
) : PokemonCatalogRepository {

    override suspend fun syncPokemonCatalog(): Result<Int> = withContext(Dispatchers.IO) {
        return@withContext try {
            val dao = database.pokemonCatalogDao()
            val items = remoteDataSource.fetchFullCatalog().map { it.toEntity() }
            database.withTransaction {
                dao.clear()
                dao.insertAll(items)
            }
            Result.success(items.size)
        } catch (e: Exception) {
            LoggerError.logError("Error sync pokemon catalog", error = e)
            Result.failure(e)
        }
    }

    override suspend fun getLastSyncAt(): Long = localDataSource.getLastSyncAt()


    override suspend fun setLastSyncAt(lastSyncTime: Long) = localDataSource.setLastSyncAt(lastSyncTime)
}
