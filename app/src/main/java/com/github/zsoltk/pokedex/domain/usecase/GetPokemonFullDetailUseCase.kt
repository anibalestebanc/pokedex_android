package com.github.zsoltk.pokedex.domain.usecase

import com.github.zsoltk.pokedex.core.common.error.UnknownException
import com.github.zsoltk.pokedex.data.mapper.combineWith
import com.github.zsoltk.pokedex.domain.model.PokemonFullDetail
import com.github.zsoltk.pokedex.domain.repository.PokemonDetailRepository
import com.github.zsoltk.pokedex.domain.repository.PokemonSpeciesRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.supervisorScope

class GetPokemonFullDetailUseCase(
    private val pokemonDetailRepository: PokemonDetailRepository,
    private val speciesRepository: PokemonSpeciesRepository
) {
    suspend operator fun invoke(id: Int): Result<PokemonFullDetail> = supervisorScope {
        val detailDeferred = async { pokemonDetailRepository.getPokemonDetail(id) }
        val speciesDeferred = async { speciesRepository.getPokemonSpecies(id) }

        val detailResult = detailDeferred.await()
        val speciesResult = speciesDeferred.await()

        if (detailResult.isSuccess && speciesResult.isSuccess) {
            val detail = detailResult.getOrThrow()
            val species = speciesResult.getOrThrow()
            Result.success(detail.combineWith(species))
        } else {
            val error = detailResult.exceptionOrNull() ?: speciesResult.exceptionOrNull() ?: UnknownException()
            Result.failure(error)
        }
    }
}
