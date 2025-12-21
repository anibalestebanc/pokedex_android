package com.github.pokemon.pokedex.data.repository

import com.github.pokemon.pokedex.BulbasaurDto
import com.github.pokemon.pokedex.PikachuDto
import com.github.pokemon.pokedex.SquirtleDto
import com.github.pokemon.pokedex.utils.LoggerError
import com.github.pokemon.pokedex.data.datasource.cache.PokemonCatalogCacheDataSource
import com.github.pokemon.pokedex.data.datasource.local.PokemonCatalogLocalDataSource
import com.github.pokemon.pokedex.data.datasource.remote.PokemonCatalogRemoteDataSource
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.github.pokemon.pokedex.data.mapper.toEntity
import com.github.pokemon.pokedex.domain.exception.PokeException.DatabaseException
import com.github.pokemon.pokedex.domain.exception.PokeException.NetworkException

class DefaultPokemonCatalogRepositoryTest {
    @MockK
    lateinit var remoteDataSource: PokemonCatalogRemoteDataSource
    @MockK
    lateinit var cacheDataSource: PokemonCatalogCacheDataSource
    @MockK
    lateinit var localDataSource: PokemonCatalogLocalDataSource
    @MockK
    lateinit var loggerError: LoggerError

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: DefaultPokemonCatalogRepository

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        repository = DefaultPokemonCatalogRepository(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
            cacheDataSource = cacheDataSource,
            loggerError = loggerError,
            dispatcher = testDispatcher
        )
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `should return success with count when remote succeeds and cache writes`() = runTest(testDispatcher) {
        // given
        val dtos = listOf(PikachuDto, BulbasaurDto, SquirtleDto)
        val expectedEntities = dtos.map { it.toEntity() }
        coEvery { remoteDataSource.fetchFullCatalog() } returns dtos
        coEvery { cacheDataSource.clearAndInsertAllCatalog(any()) } returns Unit
        every { loggerError.logError(any(), any()) } just Runs
        // when
        val result = repository.syncPokemonCatalog()

        // then
        assertTrue(result.isSuccess)
        assertEquals(expectedEntities.size, result.getOrThrow())
        coVerify(exactly = 1) { remoteDataSource.fetchFullCatalog() }
        coVerify(exactly = 1) { cacheDataSource.clearAndInsertAllCatalog(match{ it == expectedEntities }) }
        confirmVerified(remoteDataSource, cacheDataSource)
    }

    @Test
    fun `should return failure when remote fails`() = runTest(testDispatcher) {
        val expected = NetworkException("Error to get catalog")
        coEvery { remoteDataSource.fetchFullCatalog() } throws expected

        val result = repository.syncPokemonCatalog()

        assertTrue(result.isFailure)
        assertEquals(expected.message, result.exceptionOrNull()?.message)
        coVerify(exactly = 1) { remoteDataSource.fetchFullCatalog() }
        coVerify(exactly = 0) { cacheDataSource.clearAndInsertAllCatalog(any()) }
    }

    @Test
    fun `should return failure when cache write fails`() = runTest(testDispatcher) {
        coEvery { remoteDataSource.fetchFullCatalog() } returns listOf(PikachuDto)
        val expected = DatabaseException("Error to insert catalog")
        coEvery { cacheDataSource.clearAndInsertAllCatalog(any()) } throws expected

        val result = repository.syncPokemonCatalog()

        assertTrue(result.isFailure)
        assertEquals(expected.message, result.exceptionOrNull()?.message)
        coVerify(exactly = 1) { remoteDataSource.fetchFullCatalog() }
        coVerify(exactly = 1) { cacheDataSource.clearAndInsertAllCatalog(any()) }
    }

    @Test
    fun `should delegate getLastSyncAt to local`() = runTest(testDispatcher) {
        coEvery { localDataSource.getLastSyncAt() } returns 123L
        assertEquals(123L, repository.getLastSyncAt())
        coVerify { localDataSource.getLastSyncAt() }
    }

    @Test
    fun `should delegate setLastSyncAt to local`() = runTest(testDispatcher) {
        coEvery { localDataSource.setLastSyncAt(456L) } returns Unit
        repository.setLastSyncAt(456L)
        coVerify { localDataSource.setLastSyncAt(456L) }
    }
}
