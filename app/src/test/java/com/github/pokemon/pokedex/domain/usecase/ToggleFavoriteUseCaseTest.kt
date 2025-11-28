package com.github.pokemon.pokedex.domain.usecase

import com.github.pokemon.pokedex.domain.repository.PokemonDetailRepository
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ToggleFavoriteUseCaseTest {

    @MockK
    private lateinit var repository: PokemonDetailRepository
    private lateinit var toggleFavoriteUseCase: ToggleFavoriteUseCase

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        toggleFavoriteUseCase = ToggleFavoriteUseCase(repository)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `should toggle favorite successfully and return true`() = runTest {
        val pokemonId = 25
        val expectedResult = true

        coEvery { repository.toggleFavorite(pokemonId) } returns Result.success(expectedResult)

        val result = toggleFavoriteUseCase(pokemonId)

        assertTrue(result.isSuccess)
        assertEquals(expectedResult, result.getOrNull())
        coVerify(exactly = 1) { repository.toggleFavorite(pokemonId) }
    }

    @Test
    fun `should toggle favorite successfully and return false`() = runTest {
        val pokemonId = 25
        val expectedResult = false

        coEvery { repository.toggleFavorite(pokemonId) } returns Result.success(expectedResult)

        val result = toggleFavoriteUseCase(pokemonId)

        assertTrue(result.isSuccess)
        assertEquals(expectedResult, result.getOrNull())
        coVerify(exactly = 1) { repository.toggleFavorite(pokemonId) }
    }

    @Test
    fun `should return failure when toggle favorite fails`() = runTest {
        val pokemonId = 25
        val exception = Exception("Database error")

        coEvery { repository.toggleFavorite(pokemonId) } returns Result.failure(exception)

        val result = toggleFavoriteUseCase(pokemonId)

        assertTrue(result.isFailure)
        assertEquals(exception.message, result.exceptionOrNull()?.message)
        coVerify(exactly = 1) { repository.toggleFavorite(pokemonId) }
    }
}
