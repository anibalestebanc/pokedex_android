package com.github.pokemon.pokedex.data.repository

import com.github.pokemon.pokedex.BulbasaurDetailDto
import com.github.pokemon.pokedex.CharmanderDetailEntity
import com.github.pokemon.pokedex.PikachuDetailDto
import com.github.pokemon.pokedex.PikachuDetailEntity
import com.github.pokemon.pokedex.data.datasource.cache.DetailCacheDataSource
import com.github.pokemon.pokedex.data.datasource.remote.DetailRemoteDataSource
import com.github.pokemon.pokedex.data.mapper.toDomain
import com.github.pokemon.pokedex.data.mapper.toEntity
import com.github.pokemon.pokedex.domain.exception.PokeException.DatabaseException
import com.github.pokemon.pokedex.domain.exception.PokeException.NetworkException
import com.github.pokemon.pokedex.utils.PokeTimeUtil
import com.github.pokemon.pokedex.utils.RefreshDueUtil
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class OfflineFirstDetailRepositoryTest {

    @MockK
    lateinit var remoteDataSource: DetailRemoteDataSource
    @MockK lateinit var cacheDataSource: DetailCacheDataSource
    @MockK lateinit var pokeTimeUtil: PokeTimeUtil
    @MockK lateinit var refreshDue: RefreshDueUtil
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: OfflineFirstDetailRepository

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        repository = OfflineFirstDetailRepository(
            remoteDataSource = remoteDataSource,
            cacheDataSource = cacheDataSource,
            pokeTimeUtil = pokeTimeUtil,
            refreshDueUtil = refreshDue,
            ioDispatcher = testDispatcher
        )
    }

    @AfterEach
    fun tearDown() = clearAllMocks()


    @Test
    fun `should return cached detail when cache exists and is fresh on getPokemonDetail`() = runTest(testDispatcher) {
        // given
        val id = 2
        val cached = CharmanderDetailEntity
        coEvery { cacheDataSource.getDetail(id) } returns cached
        every { refreshDue.isRefreshDue(cached.lastUpdated) } returns false

        // when
        val result = repository.getPokemonDetail(id)

        // then
        assertTrue(result.isSuccess)
        assertEquals(cached.toDomain(), result.getOrThrow())
        coVerify(exactly = 0) { remoteDataSource.getDetail(any()) }
        coVerify(exactly = 0) { cacheDataSource.insertDetail(any()) }
    }

    @Test
    fun `should fetch remote and cache when cache missing on getPokemonDetail`() = runTest(testDispatcher) {
        // given
        val id = 4
        coEvery { cacheDataSource.getDetail(id) } returns null
        every { pokeTimeUtil.now() } returns 1234L
        val remoteDto = BulbasaurDetailDto
        coEvery { remoteDataSource.getDetail(id.toString()) } returns remoteDto
        coEvery { cacheDataSource.insertDetail(any()) } returns Unit

        // when
        val result = repository.getPokemonDetail(id)

        // then
        assertTrue(result.isSuccess)
        val expected = remoteDto.toDomain(pokeTimeUtil)
        assertEquals(expected, result.getOrThrow())
        coVerify(exactly = 1) { remoteDataSource.getDetail(id.toString()) }
        coVerify(exactly = 1) { cacheDataSource.insertDetail(expected.toEntity()) }
    }

    @Test
    fun `should fetch remote and cache when cache is due on getPokemonDetail`() = runTest(testDispatcher) {
        // given
        val id = 1
        val cached = PikachuDetailEntity
        coEvery { cacheDataSource.getDetail(id) } returns cached
        every { refreshDue.isRefreshDue(cached.lastUpdated) } returns true
        every { pokeTimeUtil.now() } returns 5678L
        val remoteDto = PikachuDetailDto
        coEvery { remoteDataSource.getDetail(id.toString()) } returns remoteDto
        coEvery { cacheDataSource.insertDetail(any()) } returns Unit

        // when
        val res = repository.getPokemonDetail(id)

        // then
        assertTrue(res.isSuccess)
        val expected = remoteDto.toDomain(pokeTimeUtil)
        assertEquals(expected, res.getOrThrow())
        coVerify(exactly = 1) { remoteDataSource.getDetail(id.toString()) }
        coVerify(exactly = 1) { cacheDataSource.insertDetail(expected.toEntity()) }
    }

    @Test
    fun `should log and return failure when cache get throws on getPokemonDetail`() = runTest(testDispatcher) {
        // given
        val id = 1
        val expected = DatabaseException("Error to get detail")
        coEvery { cacheDataSource.getDetail(id) } throws expected

        // when
        val res = repository.getPokemonDetail(id)

        // then
        assertTrue(res.isFailure)
        coVerify(exactly = 0) { remoteDataSource.getDetail(any()) }
        coVerify(exactly = 0) { cacheDataSource.insertDetail(any()) }
    }

    @Test
    fun `should log and return failure when remote throws on getPokemonDetail`() = runTest(testDispatcher) {
        // given
        val id = 1
        val cached = PikachuDetailEntity
        coEvery { cacheDataSource.getDetail(id) } returns cached
        every { refreshDue.isRefreshDue(cached.lastUpdated) } returns true
        val expected = NetworkException("Error to get detail")
        coEvery { remoteDataSource.getDetail(id.toString()) } throws expected

        // when
        val res = repository.getPokemonDetail(id)

        // then
        assertTrue(res.isFailure)
        coVerify(exactly = 0) { cacheDataSource.insertDetail(any()) }
    }
}
