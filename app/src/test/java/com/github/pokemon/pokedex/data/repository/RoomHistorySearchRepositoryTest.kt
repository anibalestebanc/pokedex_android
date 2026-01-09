package com.github.pokemon.pokedex.data.repository

import app.cash.turbine.test
import com.github.pokemon.pokedex.core.database.entity.HistorySearchEntity
import com.github.pokemon.pokedex.data.datasource.cache.HistorySearchCacheDataSource
import com.github.pokemon.pokedex.utils.PokeTimeUtil
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
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
    private lateinit var repository: RoomHistorySearchRepository
    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(testDispatcher)
        repository = RoomHistorySearchRepository(
            cacheDataSource = cacheDataSource,
            pokeTimeUtil = pokeTimeUtil,
            ioDispatcher = testDispatcher
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
        every { cacheDataSource.observeHistorySearch(5) } returns flowOf(
            listOf(
                HistorySearchEntity(1, "pikachu", 1L),
                HistorySearchEntity(2, "bulbasaur", 2L)
            )
        )

        // when
        val flow = repository.observeHistorySearch(limit = 5)

        // then
        flow.test {
            val list = awaitItem()
            assertEquals(listOf("pikachu", "bulbasaur"), list)
            awaitComplete()
        }
    }

    @Test
    fun `should ignore blank queries`() = runTest(testDispatcher) {
        // given
        val blanks = listOf("", "   ", " \n  ")

        blanks.forEach { q ->
            // when
            repository.saveQuery(q)
        }

        // then
        coVerify(exactly = 0) { cacheDataSource.insertQuery(any()) }
        coVerify(exactly = 0) { cacheDataSource.updateLastTimeQuery(any(), any()) }
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
