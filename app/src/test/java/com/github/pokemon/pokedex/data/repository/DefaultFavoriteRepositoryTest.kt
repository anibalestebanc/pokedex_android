package com.github.pokemon.pokedex.data.repository

import app.cash.turbine.test
import com.github.pokemon.pokedex.CharmanderDetailEntity
import com.github.pokemon.pokedex.PikachuDetailEntity
import com.github.pokemon.pokedex.data.datasource.cache.DetailCacheDataSource
import com.github.pokemon.pokedex.data.mapper.toDomain
import com.github.pokemon.pokedex.domain.exception.PokeException.DatabaseException
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DefaultFavoriteRepositoryTest {
    @MockK
    lateinit var cacheDataSource: DetailCacheDataSource
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: DefaultFavoriteRepository

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        repository = DefaultFavoriteRepository(

            cacheDataSource = cacheDataSource,
            ioDispatcher = testDispatcher,
        )
    }

    @AfterEach
    fun tearDown() = clearAllMocks()


    @Test
    fun `should map favorites list on observeFavorites`() = runTest(testDispatcher) {
        // given
        val list = listOf(PikachuDetailEntity, CharmanderDetailEntity)
        every { cacheDataSource.observeFavorites() } returns flowOf(list)

        // when
        val flow = repository.observeFavorites()

        // then
        flow.test {
            val first = awaitItem()
            assertEquals(list.map { it.toDomain() }, first)
            awaitComplete()
        }
        verify(exactly = 1) { cacheDataSource.observeFavorites() }
    }

    @Test
    fun `should delegate observeIsFavorite`() = runTest(testDispatcher) {
        // given
        val id = 1
        every { cacheDataSource.observeIsFavorite(id) } returns flowOf(true)

        // when
        val flow = repository.observeIsFavorite(id)

        // then
        flow.test {
            assertEquals(true, awaitItem())
            awaitComplete()
        }
        verify(exactly = 1) { cacheDataSource.observeIsFavorite(id) }
    }

    @Test
    fun `should set favorite and return success`() = runTest(testDispatcher) {
        // given
        val id = 2
        coEvery { cacheDataSource.setFavorite(id, true) } returns Unit

        // when
        val res = repository.saveFavorite(id, true)

        // then
        assertTrue(res.isSuccess)
        coVerify(exactly = 1) { cacheDataSource.setFavorite(id, true) }
    }

    @Test
    fun `should log and return failure when set favorite throws`() = runTest(testDispatcher) {
        // given
        val id = 1
        val expected = DatabaseException("Error to set favorite")
        coEvery { cacheDataSource.setFavorite(id, false) } throws expected

        // when
        val res = repository.saveFavorite(id, false)

        // then
        assertTrue(res.isFailure)
    }

    @Test
    fun `should toggle favorite and return new value`() = runTest(testDispatcher) {
        // given
        val id = 2
        val cached = CharmanderDetailEntity
        coEvery { cacheDataSource.getDetail(id) } returns cached
        coEvery { cacheDataSource.setFavorite(id, true) } returns Unit

        // when
        val result = repository.toggleFavorite(id)

        // then
        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { cacheDataSource.setFavorite(id, true) }
    }

    @Test
    fun `should return failure when toggle favorite not found`() = runTest(testDispatcher) {
        // given
        val id = 1
        coEvery { cacheDataSource.getDetail(id) } returns null

        // when
        val res = repository.toggleFavorite(id)

        // then
        assertTrue(res.isFailure)
        coVerify(exactly = 0) { cacheDataSource.setFavorite(any(), any()) }
    }
}
