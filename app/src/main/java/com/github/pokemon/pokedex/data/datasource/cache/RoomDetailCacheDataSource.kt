package com.github.pokemon.pokedex.data.datasource.cache

import com.github.pokemon.pokedex.core.database.dao.PokemonDetailDao
import com.github.pokemon.pokedex.core.database.entity.PokemonDetailEntity
import kotlinx.coroutines.flow.Flow

class RoomDetailCacheDataSource(
    private val pokemonDetailDao: PokemonDetailDao,
) : DetailCacheDataSource {
    override fun observeDetail(id: Int): Flow<PokemonDetailEntity?> = pokemonDetailDao.observePokemonDetail(id)

    override fun observeFavorites(): Flow<List<PokemonDetailEntity>> = pokemonDetailDao.observeFavorites()

    override fun observeIsFavorite(id: Int): Flow<Boolean> = pokemonDetailDao.observeIsFavorite(id)

    override suspend fun getDetail(id: Int): PokemonDetailEntity? = pokemonDetailDao.getPokemonDetail(id)

    override suspend fun insertDetail(entity: PokemonDetailEntity) = pokemonDetailDao.insertReplace(entity)

    override suspend fun setFavorite(id: Int, favorite: Boolean) = pokemonDetailDao.setFavorite(id, favorite)
}
