package com.github.zsoltk.pokedex.data.repository

import androidx.room.withTransaction
import com.github.zsoltk.pokedex.core.database.PokemonDatabase
import com.github.zsoltk.pokedex.data.datasource.mapper.toEntity
import com.github.zsoltk.pokedex.data.datasource.remote.PokemonCatalogRemoteDatasource
import com.github.zsoltk.pokedex.domain.repository.PokemonCatalogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultPokemonCatalogRepository(
    private val database: PokemonDatabase,
    private val remoteDataSource: PokemonCatalogRemoteDatasource,
) : PokemonCatalogRepository {
    override suspend fun getPokemonCatalog(force: Boolean): Result<Int> = withContext(Dispatchers.IO) {
        return@withContext try {
            val dao = database.pokemonCatalogDao()
            val count = dao.count()
            if (count > 0 && !force) {
                return@withContext Result.success(count)
            }

            val items = remoteDataSource.fetchFullCatalog().map { it.toEntity() }
            database.withTransaction {
                dao.clear()
                dao.insertAll(items)
            }
            Result.success(items.size)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override suspend fun refreshCatalog(): Result<Int> = getPokemonCatalog(true)
}
