package com.github.pokemon.pokedex.domain.usecase

import app.cash.turbine.test
import com.github.pokemon.pokedex.domain.repository.FavoriteRepository
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ObserveIsFavoriteUseCaseTest {

    @MockK
    private lateinit var repository: FavoriteRepository
    private lateinit var observeIsFavoriteUseCase: ObserveIsFavoriteUseCase

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        observeIsFavoriteUseCase = ObserveIsFavoriteUseCase(repository)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `should emit true when pokemon is favorite`() = runTest {
        val pokemonId = 25

        every { repository.observeIsFavorite(pokemonId) } returns flowOf(true)

        val result: Flow<Boolean> = observeIsFavoriteUseCase(pokemonId)

        result.test {
            assertTrue(awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `should emit false when pokemon is not favorite`() = runTest {
        val pokemonId = 25

        every { repository.observeIsFavorite(pokemonId) } returns flowOf(false)

        val result: Flow<Boolean> = observeIsFavoriteUseCase(pokemonId)

        result.test {
            assertFalse(awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `should emit multiple values when favorite status changes`() = runTest {
        val pokemonId = 25

        every { repository.observeIsFavorite(pokemonId) } returns flowOf(false, true, false)

        val result: Flow<Boolean> = observeIsFavoriteUseCase(pokemonId)

        result.test {
            assertFalse(awaitItem())
            assertTrue(awaitItem())
            assertFalse(awaitItem())
            awaitComplete()
        }

    }

    @Test
    fun `should observe different pokemon ids independently`() = runTest {
        val pokemonId1 = 25
        val pokemonId2 = 150

        every { repository.observeIsFavorite(pokemonId1) } returns flowOf(true)
        every { repository.observeIsFavorite(pokemonId2) } returns flowOf(false)

        val result1: Flow<Boolean> = observeIsFavoriteUseCase(pokemonId1)


        result1.test {
            assertTrue(awaitItem())
            awaitComplete()
        }

        val result2: Flow<Boolean> = observeIsFavoriteUseCase(pokemonId2)

        result2.test {
            assertFalse(awaitItem())
            awaitComplete()
        }
    }
}
