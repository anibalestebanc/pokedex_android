package com.github.pokemon.pokedex.data.repository

import app.cash.turbine.test
import com.github.pokemon.pokedex.core.common.error.DatabaseOperationException
import com.github.pokemon.pokedex.core.common.loggin.LoggerError
import com.github.pokemon.pokedex.core.database.entity.HistorySearchEntity
import com.github.pokemon.pokedex.data.datasource.cache.HistorySearchCacheDataSource
import com.github.pokemon.pokedex.utils.PokeTimeUtil
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


@OptIn(ExperimentalCoroutinesApi::class)
class RoomHistorySearchRepositoryTest {

    @MockK
    lateinit var cacheDataSource: HistorySearchCacheDataSource
    @MockK lateinit var pokeTimeUtil: PokeTimeUtil
    @MockK(relaxed = true)
    lateinit var logger: LoggerError
    private lateinit var repository: RoomHistorySearchRepository
    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(testDispatcher)
        repository = RoomHistorySearchRepository(
            historySearchCacheDataSource = cacheDataSource,
            pokeTimeUtil = pokeTimeUtil,
            loggerError = logger,
            coroutineDispatcher = testDispatcher
        )
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `should map entities to queries when getting history`() = runTest(testDispatcher) {
        // given
        every { cacheDataSource.getLastTimeSearch(5) } returns flowOf(
            listOf(
                HistorySearchEntity(1, "pikachu", 1L),
                HistorySearchEntity(2, "bulbasaur", 2L)
            )
        )

        // when
        val flow = repository.getHistorySearch(limit = 5)

        // then
        flow.test {
            val list = awaitItem()
            assertEquals(listOf("pikachu", "bulbasaur"), list)
            awaitComplete()
        }
    }

    @Test
    fun `should save normalized non-blank query and insert entity`() = runTest(testDispatcher) {
        // given
        val query = "  Pikachu  "
        val normalized = "pikachu"
        val nowTime = 123L
        every { pokeTimeUtil.now() } returns nowTime
        coEvery { cacheDataSource.insertHistorySearch(any()) } returns 42L

        // when
        repository.save(query)

        // then
        coVerify(exactly = 1) {
            cacheDataSource.insertHistorySearch(
                match { it.query == normalized && it.timestamp == nowTime }
            )
        }
        coVerify(exactly = 0) { cacheDataSource.updateLastTimeSearch(any(), any()) }
        verify(exactly = 0) { logger.logError(any<Exception>()) }
    }

    @Test
    fun `should update timestamp when insert returns -1L`() = runTest(testDispatcher) {
        // given
        val query = "Pikachu"
        val normalized = "pikachu"
        val nowTime = 999L
        every { pokeTimeUtil.now() } returns nowTime
        coEvery { cacheDataSource.insertHistorySearch(any()) } returns -1L

        // when
        repository.save(query)

        // then
        coVerify(exactly = 1) {
            cacheDataSource.insertHistorySearch(match { it.query == normalized && it.timestamp == nowTime })
        }
        coVerify(exactly = 1) { cacheDataSource.updateLastTimeSearch(normalized, nowTime) }
        verify(exactly = 0) { logger.logError(any<Exception>()) }
    }

    @Test
    fun `should ignore blank queries`() = runTest(testDispatcher) {
        // given
        val blanks = listOf("", "   ", " \n  ")

        blanks.forEach { q ->
            // when
            repository.save(q)
        }

        // then
        coVerify(exactly = 0) { cacheDataSource.insertHistorySearch(any()) }
        coVerify(exactly = 0) { cacheDataSource.updateLastTimeSearch(any(), any()) }
        verify(exactly = 0) { logger.logError(any<Exception>()) }
    }

    @Test
    fun `should log error when save throws`() = runTest(testDispatcher) {
        // given
        val query = "Charmander"
        every { pokeTimeUtil.now() } returns 1L
        val expected = DatabaseOperationException("Error to insert query")
        coEvery { cacheDataSource.insertHistorySearch(any()) } throws expected
        every { logger.logError(any<Exception>()) } just Runs

        // when
        repository.save(query)

        // then
        verify(exactly = 1) {
            logger.logError(match<DatabaseOperationException> {
                it.message?.contains("Error saving search query: $query") == true &&
                    it.cause === expected
            })
        }
        coVerify(exactly = 0) { cacheDataSource.updateLastTimeSearch(any(), any()) }
    }

    @Test
    fun `should normalize query on remove`() = runTest(testDispatcher) {
        // given
        coEvery { cacheDataSource.deleteByQuery(any()) } returns Unit

        // when
        repository.remove("  BULBASAUR ")

        // then
        coVerify(exactly = 1) { cacheDataSource.deleteByQuery("bulbasaur") }
    }

    @Test
    fun `should clear all`() = runTest(testDispatcher) {
        // given
        coEvery { cacheDataSource.clearAll() } returns Unit

        // when
        repository.clearAll()

        // then
        coVerify(exactly = 1) { cacheDataSource.clearAll() }
    }
}
