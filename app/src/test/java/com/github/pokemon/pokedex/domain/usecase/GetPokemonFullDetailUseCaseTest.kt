package com.github.pokemon.pokedex.domain.usecase

import com.github.pokemon.pokedex.Charmander
import com.github.pokemon.pokedex.CharmanderSpecies
import com.github.pokemon.pokedex.Pikachu
import com.github.pokemon.pokedex.PikachuSpecies
import com.github.pokemon.pokedex.data.mapper.combineWith
import com.github.pokemon.pokedex.domain.exception.PokeException.NetworkException
import com.github.pokemon.pokedex.domain.exception.PokeException.NotFoundException
import com.github.pokemon.pokedex.domain.exception.PokeException.ServerException
import com.github.pokemon.pokedex.domain.exception.PokeException.UnknownException
import com.github.pokemon.pokedex.domain.repository.PokemonDetailRepository
import com.github.pokemon.pokedex.domain.repository.SpeciesRepository
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class GetPokemonFullDetailUseCaseTest {

    @MockK
    lateinit var detailRepository: PokemonDetailRepository

    @MockK
    lateinit var speciesRepository: SpeciesRepository

    private lateinit var getPokemonFullDetailUseCase: GetPokemonFullDetailUseCase

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        getPokemonFullDetailUseCase = GetPokemonFullDetailUseCase(
            pokemonDetailRepository = detailRepository,
            speciesRepository = speciesRepository,
        )
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `should return combined detail when both repositories succeed`() = runTest {
        // given
        val id = 25
        val detail = Pikachu
        val species = PikachuSpecies
        val fullDetail = Pikachu.combineWith(PikachuSpecies)
        coEvery { detailRepository.getPokemonDetail(id) } returns Result.success(detail)
        coEvery { speciesRepository.getSpecies(id) } returns Result.success(species)

        // when
        val result = getPokemonFullDetailUseCase(id)

        // then
        assertTrue(result.isSuccess)
        assertEquals(fullDetail, result.getOrThrow())
        coVerify(exactly = 1) { detailRepository.getPokemonDetail(id) }
        coVerify(exactly = 1) { speciesRepository.getSpecies(id) }
    }

    @Test
    fun `should return failure when detail fails`() = runTest {
        // given
        val id = 1
        val expected = NetworkException("Error to get pokemon detail")
        coEvery { detailRepository.getPokemonDetail(id) } returns Result.failure(expected)
        coEvery { speciesRepository.getSpecies(id) } returns Result.success(CharmanderSpecies)

        // when
        val result = getPokemonFullDetailUseCase(id)

        // then
        assertTrue(result.isFailure)
        assertEquals(expected.message, result.exceptionOrNull()?.message)
        coVerify(exactly = 1) { detailRepository.getPokemonDetail(id) }
        coVerify(exactly = 1) { speciesRepository.getSpecies(id) }
    }

    @Test
    fun `should return failure when species fails`() = runTest {
        // given
        val id = 4
        val expected = NotFoundException("Pokemon not found")
        coEvery { detailRepository.getPokemonDetail(id) } returns Result.success(Charmander)
        coEvery { speciesRepository.getSpecies(id) } returns Result.failure(expected)

        // when
        val result = getPokemonFullDetailUseCase(id)

        // then
        assertTrue(result.isFailure)
        assertEquals(expected.message, result.exceptionOrNull()?.message)
        coVerify(exactly = 1) { detailRepository.getPokemonDetail(id) }
        coVerify(exactly = 1) { speciesRepository.getSpecies(id) }
    }

    @Test
    fun `should return failure with one of the errors when both fail`() = runTest {
        // given
        val id = 7
        val detailErr = UnknownException("Unknown error")
        val speciesErr = ServerException("Internal server error")
        coEvery { detailRepository.getPokemonDetail(id) } returns Result.failure(detailErr)
        coEvery { speciesRepository.getSpecies(id) } returns Result.failure(speciesErr)

        // when
        val result = getPokemonFullDetailUseCase(id)

        // then
        assertTrue(result.isFailure)
        assertEquals(detailErr.message, result.exceptionOrNull()?.message)
        coVerify(exactly = 1) { detailRepository.getPokemonDetail(id) }
        coVerify(exactly = 1) { speciesRepository.getSpecies(id) }
    }

    @Test
    fun `should use UnknownException when both results are not success`() = runTest {
        // given
        val id = 10

        coEvery { detailRepository.getPokemonDetail(id) } returns Result.failure(UnknownException())
        coEvery { speciesRepository.getSpecies(id) } returns Result.failure(UnknownException())

        // when
        val result = getPokemonFullDetailUseCase(id)

        // then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is Exception)
    }
}
