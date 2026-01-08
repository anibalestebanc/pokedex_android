package com.github.pokemon.pokedex.data.datasource.remote

import com.github.pokemon.pokedex.data.datasource.remote.dto.DetailDto

interface DetailRemoteDataSource {
    suspend fun getDetail(idOrName: String): DetailDto
}
