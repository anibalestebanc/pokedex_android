package com.github.pokemon.pokedex.data.repository

import app.cash.turbine.test
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
import com.github.pokemon.pokedex.domain.model.PokemonDetail
import com.github.pokemon.pokedex.utils.PokeTimeUtil
import com.github.pokemon.pokedex.utils.RefreshDueUtil
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class OfflineFirstPokemonDetailRepositoryTest {

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
            coroutineDispatcher = testDispatcher
        )
    }

    @AfterEach
    fun tearDown() = clearAllMocks()


    @Test
    fun `should emit cached detail on observe when cache exists and is fresh`() = runTest(testDispatcher) {
        // given
        val id = 1
        val pikachuEntity = PikachuDetailEntity
        every { cacheDataSource.observeDetail(id) } returns flowOf(pikachuEntity)
        coEvery { cacheDataSource.getDetail(id) } returns pikachuEntity
        every { refreshDue.isRefreshDue(pikachuEntity.lastUpdated) } returns false

        // when
        val flow: Flow<PokemonDetail?> = repository.observePokemonDetail(id)

        // then
        flow.test {
            val first = awaitItem()
            assertEquals(pikachuEntity.toDomain(), first)
            awaitComplete()
        }
        coVerify(exactly = 1) { cacheDataSource.getDetail(id) }
        coVerify(exactly = 0) { remoteDataSource.getDetail(any()) }
    }

    @Test
    fun `should trigger remote load on observe start when cache is missing`() = runTest(testDispatcher) {
        // given
        val id = 1
        every { cacheDataSource.observeDetail(id) } returns flow { emit(null) }
        coEvery { cacheDataSource.getDetail(id) } returns null
        every { pokeTimeUtil.now() } returns 1L
        val remoteDto = BulbasaurDetailDto
        val entity = remoteDto.toDomain(pokeTimeUtil).toEntity()
        coEvery { remoteDataSource.getDetail(id.toString()) } returns remoteDto
        coEvery { cacheDataSource.insertDetail(any()) } returns Unit

        // when
        val flow = repository.observePokemonDetail(id)

        // then
        flow.test {
            assertEquals(null, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
        coVerify(exactly = 2) { cacheDataSource.getDetail(id) }
        coVerify(exactly = 1) { remoteDataSource.getDetail(id.toString()) }
        coVerify(exactly = 1) { cacheDataSource.insertDetail(entity) }
    }

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
        val res = repository.setFavorite(id, true)

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
        val res = repository.setFavorite(id, false)

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
        assertEquals(true, result.getOrThrow())
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
