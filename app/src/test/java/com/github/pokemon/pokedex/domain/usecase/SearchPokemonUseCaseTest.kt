package com.github.pokemon.pokedex.domain.usecase

import androidx.paging.PagingData
import app.cash.turbine.test
import com.github.pokemon.pokedex.BulbasaurCatalog
import com.github.pokemon.pokedex.PikachuCatalog
import com.github.pokemon.pokedex.SquirtleCatalog
import com.github.pokemon.pokedex.domain.exception.PokeException.DatabaseException
import com.github.pokemon.pokedex.domain.model.PokemonCatalog
import com.github.pokemon.pokedex.domain.model.SearchQuery
import com.github.pokemon.pokedex.domain.repository.CatalogRepository
import com.github.pokemon.pokedex.toListUsingDiffer
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.MockK
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
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


@OptIn(ExperimentalCoroutinesApi::class)
class SearchPokemonUseCaseTest {

    @MockK
    lateinit var repository: CatalogRepository

    private lateinit var searchPokemonPagedUseCase: SearchPokemonPagedUseCase

    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(testDispatcher)
        searchPokemonPagedUseCase = SearchPokemonPagedUseCase(repository)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `should delegate to repository and emit paging data when query is provided`() = runTest(testDispatcher) {
        // given
        val query = "pika"
        val searchQuery = SearchQuery.ByName(query)
        val items = listOf(PikachuCatalog, SquirtleCatalog)
        every { repository.searchPokemonPaged(searchQuery) } returns flowOf(PagingData.from(items))

        // when
        val flow: Flow<PagingData<PokemonCatalog>> = searchPokemonPagedUseCase(query)

        // then
        flow.test {
            val page = awaitItem()
            val list = page.toListUsingDiffer(testDispatcher)
            assertEquals(items, list)
            awaitComplete()
        }
    }

    @Test
    fun `should emit empty paging data when repository returns empty`() = runTest(testDispatcher) {
        // given
        val query = " "
        val searchQuery = SearchQuery.All
        every { repository.searchPokemonPaged(searchQuery) } returns flowOf(PagingData.from(emptyList()))

        // when
        val flow = searchPokemonPagedUseCase(query)

        // then
        flow.test {
            val page = awaitItem()
            val list = page.toListUsingDiffer(testDispatcher)
            assertTrue(list.isEmpty())
            awaitComplete()
        }
    }

    @Test
    fun `should propagate error when repository throws`() = runTest(testDispatcher) {
        // given
        val query = "char"
        val searchQuery = SearchQuery.ByName(query)
        val expected = DatabaseException("Error to search pokemon")
        every { repository.searchPokemonPaged(searchQuery) } returns flow {
            throw expected
        }

        // when
        val flow = searchPokemonPagedUseCase(query)

        // then
        flow.test {
            val err = awaitError()
            assertEquals(expected.message, err.message)
        }
    }

    @Test
    fun `should reflect paged updates when repository emits multiple paging snapshots`() = runTest(testDispatcher) {
        // given
        val query = "char"
        val searchQuery = SearchQuery.ByName(query)
        val snapshot1 = PagingData.from(listOf(BulbasaurCatalog))
        val snapshot2 = PagingData.from(listOf(BulbasaurCatalog, PikachuCatalog))
        every { repository.searchPokemonPaged(searchQuery) } returns flowOf(snapshot1, snapshot2)

        // when
        val flow = searchPokemonPagedUseCase(query)

        // then
        flow.test {
            val first = awaitItem().toListUsingDiffer(testDispatcher)
            assertEquals(listOf(BulbasaurCatalog), first)

            val second = awaitItem().toListUsingDiffer(testDispatcher)
            assertEquals(
                listOf(BulbasaurCatalog, PikachuCatalog),
                second,
            )
            awaitComplete()
        }
    }

    @Test
    fun `should call repository with the exact query`() = runTest(testDispatcher) {
        // given
        val query = "squirt"
        val searchQuery = SearchQuery.ByName(query)
        every { repository.searchPokemonPaged(searchQuery) } returns flowOf(PagingData.from(emptyList()))

        // when
        val flow = searchPokemonPagedUseCase(query)

        // then
        flow.test {
            awaitItem()
            awaitComplete()
        }
    }
}
