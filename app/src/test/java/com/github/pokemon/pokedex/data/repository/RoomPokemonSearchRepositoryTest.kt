package com.github.pokemon.pokedex.data.repository

import androidx.paging.PagingData
import app.cash.turbine.test
import com.github.pokemon.pokedex.BulbasaurCatalog
import com.github.pokemon.pokedex.PikachuCatalog
import com.github.pokemon.pokedex.SquirtleCatalog
import com.github.pokemon.pokedex.data.datasource.cache.SearchCacheDatasource
import com.github.pokemon.pokedex.domain.exception.PokeException.DatabaseException
import com.github.pokemon.pokedex.domain.model.PokemonCatalog
import com.github.pokemon.pokedex.toListUsingDiffer
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


@OptIn(ExperimentalCoroutinesApi::class)
class RoomPokemonSearchRepositoryTest {
    @MockK
    lateinit var cacheDatasource: SearchCacheDatasource

    private lateinit var repository: RoomSearchRepository

    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(testDispatcher)
        repository = RoomSearchRepository(cacheDatasource)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `should delegate to cache datasource with the same query`() = runTest(testDispatcher) {
        // given
        val query = "pika"
        every { cacheDatasource.searchPaged(query) } returns flowOf(PagingData.from(emptyList()))

        // when
        val flow = repository.searchPaged(query)

        // then
        flow.test {
            awaitItem()
            awaitComplete()
        }
        verify(exactly = 1) { cacheDatasource.searchPaged(query) }
    }

    @Test
    fun `should emit paging data with items when cache returns results`() = runTest(testDispatcher) {
        // given
        val items = listOf(PikachuCatalog, BulbasaurCatalog)
        every { cacheDatasource.searchPaged(null) } returns flowOf(PagingData.from(items))

        // when
        val flow: Flow<PagingData<PokemonCatalog>> = repository.searchPaged(null)

        // then
        flow.test {
            val page = awaitItem()
            val list = page.toListUsingDiffer(testDispatcher)
            assertEquals(items, list)
            awaitComplete()
        }
        verify(exactly = 1) { cacheDatasource.searchPaged(null) }
    }

    @Test
    fun `should emit empty when cache returns empty paging data`() = runTest(testDispatcher) {
        // given
        every { cacheDatasource.searchPaged("") } returns flowOf(PagingData.from(emptyList()))

        // when
        val flow = repository.searchPaged("")

        // then
        flow.test {
            val page = awaitItem()
            val list = page.toListUsingDiffer(testDispatcher)
            assertTrue(list.isEmpty())
            awaitComplete()
        }
        verify(exactly = 1) { cacheDatasource.searchPaged("") }
    }

    @Test
    fun `should propagate errors from cache`() = runTest(testDispatcher) {
        // given
        val query = "char"
        val expected = DatabaseException("Error to get catalog")
        every { cacheDatasource.searchPaged(query) } returns flow { throw expected }

        // when
        val flow = repository.searchPaged(query)

        // then
        flow.test {
            val err = awaitError()
            assertEquals(expected.message, err.message)
        }
        verify(exactly = 1) { cacheDatasource.searchPaged(query) }
    }

    @Test
    fun `should reflect updated snapshots when cache emits multiple paging data`() = runTest(testDispatcher) {
        // given
        val query = "bulb"
        val paging1 = PagingData.from(listOf(BulbasaurCatalog))
        val paging2 = PagingData.from(listOf(BulbasaurCatalog, SquirtleCatalog))
        every { cacheDatasource.searchPaged(query) } returns flowOf(paging1, paging2)

        // when
        val flow = repository.searchPaged(query)

        // then
        flow.test {
            val first = awaitItem().toListUsingDiffer(testDispatcher)
            assertEquals(listOf(BulbasaurCatalog), first)

            val second = awaitItem().toListUsingDiffer(testDispatcher)
            assertEquals(listOf(BulbasaurCatalog, SquirtleCatalog), second)
            awaitComplete()
        }
        verify(exactly = 1) { cacheDatasource.searchPaged(query) }
    }
}
