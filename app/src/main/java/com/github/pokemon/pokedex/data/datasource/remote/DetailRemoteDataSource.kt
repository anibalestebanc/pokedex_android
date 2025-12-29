package com.github.pokemon.pokedex.data.datasource.remote

import com.github.pokemon.pokedex.data.datasource.remote.dto.PokemonDetailDto

interface DetailRemoteDataSource {
    suspend fun getDetail(idOrName: String): PokemonDetailDto
}
