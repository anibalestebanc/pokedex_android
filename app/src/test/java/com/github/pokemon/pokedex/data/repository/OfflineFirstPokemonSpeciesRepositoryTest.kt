package com.github.pokemon.pokedex.data.repository

import com.github.pokemon.pokedex.CharmanderSpeciesDto
import com.github.pokemon.pokedex.CharmanderSpeciesEntity
import com.github.pokemon.pokedex.PikachuSpeciesDto
import com.github.pokemon.pokedex.PikachuSpeciesEntity
import com.github.pokemon.pokedex.core.common.error.DatabaseOperationException
import com.github.pokemon.pokedex.core.common.error.NetworkException
import com.github.pokemon.pokedex.core.common.loggin.LoggerError
import com.github.pokemon.pokedex.data.datasource.cache.SpeciesCacheDataSource
import com.github.pokemon.pokedex.data.datasource.remote.PokemonSpeciesRemoteDataSource
import com.github.pokemon.pokedex.data.mapper.toDomain
import com.github.pokemon.pokedex.data.mapper.toEntity
import com.github.pokemon.pokedex.utils.PokeTimeUtil
import com.github.pokemon.pokedex.utils.RefreshDueUtil
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.verify
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class OfflineFirstPokemonSpeciesRepositoryTest {

    @MockK
    lateinit var remoteDataSource: PokemonSpeciesRemoteDataSource
    @MockK
    lateinit var cacheDataSource: SpeciesCacheDataSource
    @MockK(relaxed = true)
    lateinit var loggerError: LoggerError
    @MockK
    lateinit var pokeTimeUtil: PokeTimeUtil
    @MockK
    lateinit var refreshDue: RefreshDueUtil

    private val dispatcher = StandardTestDispatcher()
    private lateinit var repository: OfflineFirstPokemonSpeciesRepository

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        repository = OfflineFirstPokemonSpeciesRepository(
            remoteDataSource = remoteDataSource,
            speciesCacheDataSource = cacheDataSource,
            loggerError = loggerError,
            pokeTimeUtil = pokeTimeUtil,
            refreshDueUtil = refreshDue,
            coroutineDispatcher = dispatcher
        )
    }

    @AfterEach
    fun tearDown() = clearAllMocks()


    @Test
    fun `should return cached species when cache exists and is not due`() = runTest(dispatcher) {
        // given
        val id = 1
        val pikachuEntity = PikachuSpeciesEntity
        coEvery { cacheDataSource.getSpecieById(id) } returns pikachuEntity
        every { refreshDue.isRefreshDue(pikachuEntity.lastUpdated) } returns false

        // when
        val result = repository.getPokemonSpecies(id)

        // then
        assertTrue(result.isSuccess)
        assertEquals(pikachuEntity.toDomain(), result.getOrThrow())
        coVerify(exactly = 1) { cacheDataSource.getSpecieById(id) }
        verify(exactly = 1) { refreshDue.isRefreshDue(pikachuEntity.lastUpdated) }
        coVerify(exactly = 0) { remoteDataSource.getSpecies(any()) }
        coVerify(exactly = 0) { cacheDataSource.insertSpecie(any()) }
    }

    @Test
    fun `should fetch remote and cache when cache missing`() = runTest(dispatcher) {
        // given
        val id = 1
        coEvery { cacheDataSource.getSpecieById(id) } returns null
        every { pokeTimeUtil.now() } returns 999L
        val pikachuDto = PikachuSpeciesDto
        coEvery { remoteDataSource.getSpecies(id.toString()) } returns pikachuDto
        coEvery { cacheDataSource.insertSpecie(any()) } returns Unit
        every { refreshDue.isRefreshDue(any()) } returns true

        // when
        val result = repository.getPokemonSpecies(id)

        // then
        val expected = pikachuDto.toDomain(pokeTimeUtil)
        assertTrue(result.isSuccess)
        assertEquals(expected, result.getOrThrow())
        coVerify(exactly = 1) { remoteDataSource.getSpecies(id.toString()) }
        coVerify(exactly = 1) { cacheDataSource.insertSpecie(expected.toEntity()) }
    }

    @Test
    fun `should fetch remote and cache when cache is due`() = runTest(dispatcher) {
        // given
        val id = 2
        val charmanderEntity = CharmanderSpeciesEntity
        coEvery { cacheDataSource.getSpecieById(id) } returns charmanderEntity
        every { refreshDue.isRefreshDue(charmanderEntity.lastUpdated) } returns true
        every { pokeTimeUtil.now() } returns 2000L
        val charmanderDto = CharmanderSpeciesDto
        coEvery { remoteDataSource.getSpecies(id.toString()) } returns charmanderDto
        coEvery { cacheDataSource.insertSpecie(any()) } returns Unit

        // when
        val result = repository.getPokemonSpecies(id)

        // then
        val expected = charmanderDto.toDomain(pokeTimeUtil)
        assertTrue(result.isSuccess)
        assertEquals(expected, result.getOrThrow())
        coVerify(exactly = 1) { cacheDataSource.getSpecieById(id) }
        verify(exactly = 1) { refreshDue.isRefreshDue(charmanderEntity.lastUpdated) }
        coVerify(exactly = 1) { remoteDataSource.getSpecies(id.toString()) }
        coVerify(exactly = 1) { cacheDataSource.insertSpecie(expected.toEntity()) }
    }

    @Test
    fun `should log and return failure when cache get throws`() = runTest(dispatcher) {
        // given
        val id = 1
        val expected = DatabaseOperationException("Error to get species")
        coEvery { cacheDataSource.getSpecieById(id) } throws expected
        every { loggerError.logError(any(), any()) } just Runs

        // when
        val result = repository.getPokemonSpecies(id)

        // then
        assertTrue(result.isFailure)
        verify { loggerError.logError("Error getting pokemon species with id: $id", expected) }
        coVerify(exactly = 0) { remoteDataSource.getSpecies(any()) }
        coVerify(exactly = 0) { cacheDataSource.insertSpecie(any()) }
    }

    @Test
    fun `should log and return failure when remote throws`() = runTest(dispatcher) {
        // given
        val id = 1
        val pikachuEntity = PikachuSpeciesEntity
        coEvery { cacheDataSource.getSpecieById(id) } returns pikachuEntity
        every { refreshDue.isRefreshDue(pikachuEntity.lastUpdated) } returns true
        val expected = NetworkException("Error to get species")
        coEvery { remoteDataSource.getSpecies(id.toString()) } throws expected
        every { loggerError.logError(any(), any()) } just Runs

        // when
        val result = repository.getPokemonSpecies(id)

        // then
        assertTrue(result.isFailure)
        verify { loggerError.logError("Error getting pokemon species with id: $id", expected) }
        coVerify(exactly = 0) { cacheDataSource.insertSpecie(any()) }
    }
}
