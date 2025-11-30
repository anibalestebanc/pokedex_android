package com.github.pokemon.pokedex.domain.usecase

import androidx.paging.PagingData
import app.cash.turbine.test
import com.github.pokemon.pokedex.BulbasaurCatalog
import com.github.pokemon.pokedex.PikachuCatalog
import com.github.pokemon.pokedex.SquirtleCatalog
import com.github.pokemon.pokedex.core.common.error.DatabaseOperationException
import com.github.pokemon.pokedex.domain.model.PokemonCatalog
import com.github.pokemon.pokedex.domain.repository.SearchPokemonRepository
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
    lateinit var repository: SearchPokemonRepository

    private lateinit var searchPokemonUseCase: SearchPokemonUseCase

    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(testDispatcher)
        searchPokemonUseCase = SearchPokemonUseCase(repository)
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
        val items = listOf(PikachuCatalog, SquirtleCatalog)
        every { repository.searchPaged(query) } returns flowOf(PagingData.from(items))

        // when
        val flow: Flow<PagingData<PokemonCatalog>> = searchPokemonUseCase(query)

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
        every { repository.searchPaged(null) } returns flowOf(PagingData.from(emptyList()))

        // when
        val flow = searchPokemonUseCase(null)

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
        val expected = DatabaseOperationException("Error to search pokemon")
        every { repository.searchPaged(query) } returns flow {
            throw expected
        }

        // when
        val flow = searchPokemonUseCase(query)

        // then
        flow.test {
            val err = awaitError()
            assertEquals(expected.message, err.message)
        }
    }

    @Test
    fun `should reflect paged updates when repository emits multiple paging snapshots`() = runTest(testDispatcher) {
        // given
        val snapshot1 = PagingData.from(listOf(BulbasaurCatalog))
        val snapshot2 = PagingData.from(listOf(BulbasaurCatalog, PikachuCatalog))
        every { repository.searchPaged("") } returns flowOf(snapshot1, snapshot2)

        // when
        val flow = searchPokemonUseCase("")

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
        every { repository.searchPaged(query) } returns flowOf(PagingData.from(emptyList()))

        // when
        val flow = searchPokemonUseCase(query)

        // then
        flow.test {
            awaitItem()
            awaitComplete()
        }
    }
}
