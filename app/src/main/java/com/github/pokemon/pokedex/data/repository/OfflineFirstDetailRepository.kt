package com.github.pokemon.pokedex.data.repository

import com.github.pokemon.pokedex.data.datasource.cache.DetailCacheDataSource
import com.github.pokemon.pokedex.data.datasource.remote.DetailRemoteDataSource
import com.github.pokemon.pokedex.data.mapper.toDomain
import com.github.pokemon.pokedex.data.mapper.toEntity
import com.github.pokemon.pokedex.domain.model.PokemonDetail
import com.github.pokemon.pokedex.domain.repository.PokemonDetailRepository
import com.github.pokemon.pokedex.utils.PokeTimeUtil
import com.github.pokemon.pokedex.utils.RefreshDueUtil
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext

class OfflineFirstDetailRepository(
    private val remoteDataSource: DetailRemoteDataSource,
    private val cacheDataSource: DetailCacheDataSource,
    private val pokeTimeUtil: PokeTimeUtil,
    private val refreshDueUtil: RefreshDueUtil,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : PokemonDetailRepository {

    override fun observePokemonDetail(id: Int): Flow<PokemonDetail?> =
        cacheDataSource.observeDetail(id)
            .map { entity -> entity?.toDomain() }
            .onStart {
                if (cacheDataSource.getDetail(id) == null) {
                    getPokemonDetail(id)
                }
            }.flowOn(ioDispatcher)

    override suspend fun getPokemonDetail(id: Int): Result<PokemonDetail> =
        withContext(ioDispatcher) {
            runCatching {
                val detailEntity = cacheDataSource.getDetail(id)
                if (detailEntity != null && !refreshDueUtil.isRefreshDue(detailEntity.lastUpdated)) {
                    return@runCatching detailEntity.toDomain()
                }
                val remoteDetailDto = remoteDataSource.getDetail(id.toString())
                val detail = remoteDetailDto.toDomain(pokeTimeUtil)
                cacheDataSource.insertDetail(detail.toEntity())
                detail
            }
        }
}
