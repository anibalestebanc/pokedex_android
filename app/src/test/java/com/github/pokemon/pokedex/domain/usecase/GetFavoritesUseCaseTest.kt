package com.github.pokemon.pokedex.domain.usecase

import app.cash.turbine.test
import com.github.pokemon.pokedex.Bulbasaur
import com.github.pokemon.pokedex.Charmander
import com.github.pokemon.pokedex.Pikachu
import com.github.pokemon.pokedex.Squirtle
import com.github.pokemon.pokedex.core.common.error.DatabaseOperationException
import com.github.pokemon.pokedex.domain.model.PokemonDetail
import com.github.pokemon.pokedex.domain.repository.PokemonDetailRepository
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetFavoritesUseCaseTest {

    @MockK
    lateinit var repository: PokemonDetailRepository

    private lateinit var getFavoriteUseCase: GetFavoritesUseCase

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        getFavoriteUseCase = GetFavoritesUseCase(repository)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `should emit true when pokemon is favorite`() = runTest {
        // given
        val favorites = listOf(Pikachu)
        every { repository.observeFavorites() } returns flowOf(favorites)

        // when
        val result: Flow<List<PokemonDetail>> = getFavoriteUseCase()

        // then
        result.test {
            val first = awaitItem()
            assertTrue(first.isNotEmpty())
            assertTrue(first.all { it.isFavorite })
            awaitComplete()
        }
    }

    @Test
    fun `should re-emit the same sequence when repository emits multiple updates`() = runTest {
        // given
        val seq = listOf(
            emptyList(),
            listOf(Bulbasaur),
            listOf(Bulbasaur, Charmander),
        )
        every { repository.observeFavorites() } returns flow {
            seq.forEach { emit(it) }
        }

        // when
        val result = getFavoriteUseCase()

        // then
        result.test {
            assertEquals(emptyList<PokemonDetail>(), awaitItem())
            assertEquals(listOf(Bulbasaur), awaitItem())
            assertEquals(listOf(Bulbasaur, Charmander), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `should emit empty list when there are no favorites`() = runTest {
        // given
        every { repository.observeFavorites() } returns flowOf(emptyList())

        // when
        val result = getFavoriteUseCase()

        // then
        result.test {
            assertTrue(awaitItem().isEmpty())
            awaitComplete()
        }
    }

    @Test
    fun `should propagate error when repository throws`() = runTest {
        // given
        val expected = DatabaseOperationException("Error to get favorites")
        every { repository.observeFavorites() } returns flow { throw expected }

        // when
        val result = getFavoriteUseCase()

        // then
        result.test {
            val err = awaitError()
            assertEquals(expected.message, err.message)
        }
    }

    @Test
    fun `should allow cancellation when repository emits indefinitely`() = runTest {
        val shared = MutableSharedFlow<List<PokemonDetail>>(replay = 1)

        shared.emit(listOf(Squirtle))

        val emitter : Job = launch {
            while (true) {
                shared.emit(listOf(Pikachu))
            }
        }
        // given
        every { repository.observeFavorites() } returns shared

        // when
        val result = getFavoriteUseCase()

        // then
        result.test {
            val first = awaitItem()
            assertEquals(listOf(Squirtle), first)
            cancelAndIgnoreRemainingEvents()
        }

        emitter.cancel()
    }
}
