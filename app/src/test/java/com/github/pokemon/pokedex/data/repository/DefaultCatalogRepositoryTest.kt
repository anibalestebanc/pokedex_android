package com.github.pokemon.pokedex.data.repository

import com.github.pokemon.pokedex.BulbasaurDto
import com.github.pokemon.pokedex.PikachuDto
import com.github.pokemon.pokedex.SquirtleDto
import com.github.pokemon.pokedex.data.datasource.cache.CatalogCacheDataSource
import com.github.pokemon.pokedex.data.datasource.remote.CatalogRemoteDataSource
import com.github.pokemon.pokedex.data.mapper.toEntity
import com.github.pokemon.pokedex.domain.exception.PokeException.DatabaseException
import com.github.pokemon.pokedex.domain.exception.PokeException.NetworkException
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DefaultCatalogRepositoryTest {
    @MockK
    lateinit var remoteDataSource: CatalogRemoteDataSource
    @MockK
    lateinit var cacheDataSource: CatalogCacheDataSource
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: DefaultCatalogRepository

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        repository = DefaultCatalogRepository(
            remoteDataSource = remoteDataSource,
            cacheDataSource = cacheDataSource,
            coroutineDispatcher = testDispatcher
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
        // when
        val result = repository.syncCatalog()

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

        val result = repository.syncCatalog()

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

        val result = repository.syncCatalog()

        assertTrue(result.isFailure)
        assertEquals(expected.message, result.exceptionOrNull()?.message)
        coVerify(exactly = 1) { remoteDataSource.fetchFullCatalog() }
        coVerify(exactly = 1) { cacheDataSource.clearAndInsertAllCatalog(any()) }
    }
}
