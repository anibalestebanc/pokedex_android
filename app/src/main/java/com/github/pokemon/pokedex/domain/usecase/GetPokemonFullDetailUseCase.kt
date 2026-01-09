package com.github.pokemon.pokedex.domain.usecase

import com.github.pokemon.pokedex.data.mapper.combineWith
import com.github.pokemon.pokedex.domain.exception.PokeException.UnknownException
import com.github.pokemon.pokedex.domain.model.PokemonFullDetail
import com.github.pokemon.pokedex.domain.repository.PokemonDetailRepository
import com.github.pokemon.pokedex.domain.repository.SpeciesRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.supervisorScope

class GetPokemonFullDetailUseCase(
    private val pokemonDetailRepository: PokemonDetailRepository,
    private val speciesRepository: SpeciesRepository
) {
    suspend operator fun invoke(id: Int): Result<PokemonFullDetail> = supervisorScope {
        val detailDeferred = async { pokemonDetailRepository.getPokemonDetail(id) }
        val speciesDeferred = async { speciesRepository.getSpecies(id) }

        val detailResult = detailDeferred.await()
        val speciesResult = speciesDeferred.await()

        if (detailResult.isSuccess && speciesResult.isSuccess) {
            val detail = detailResult.getOrThrow()
            val species = speciesResult.getOrThrow()
            Result.success(detail.combineWith(species))
        } else {
            val error = detailResult.exceptionOrNull()
                ?: speciesResult.exceptionOrNull()
                ?: UnknownException()
            Result.failure(error)
        }
    }
}
