package com.github.pokemon.pokedex.domain.usecase

import com.github.pokemon.pokedex.core.work.SyncCatalogWorkScheduler
import com.github.pokemon.pokedex.domain.repository.SyncCatalogRepository
import com.github.pokemon.pokedex.utils.PokeTimeUtil
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


@OptIn(ExperimentalCoroutinesApi::class)
class EnqueueDailySyncCatalogUseCaseTest {

    @MockK
    lateinit var pokeTimeUtil: PokeTimeUtil
    @MockK
    lateinit var repository: SyncCatalogRepository

    @MockK(relaxed = true)
    lateinit var scheduler: SyncCatalogWorkScheduler

    private lateinit var syncCatalogUseCase: EnqueueDailySyncCatalogUseCase

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        syncCatalogUseCase = EnqueueDailySyncCatalogUseCase(repository, scheduler, pokeTimeUtil)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `should enqueue immediate sync when last sync is zero`() = runTest {
        // given
        coEvery { repository.getLastSyncAt() } returns 0L
        every { pokeTimeUtil.now() } returns 123_456L
        val minInterval = 86_400_000L // ONE_DAY

        // when
        syncCatalogUseCase(minInterval)

        // then
        coVerify(exactly = 1) { repository.getLastSyncAt() }
        coVerify(exactly = 1) { scheduler.enqueueImmediateSync() }
        coVerify(exactly = 0) { scheduler.enqueueDailySync() }
    }

    @Test
    fun `should enqueue daily sync when elapsed time is greater or equal to minInterval`() = runTest {
        // given
        val lastSync = 1_000_000L
        val now = 1_000_000L + 10_000L
        val minInterval = 10_000L
        coEvery { repository.getLastSyncAt() } returns lastSync
        every { pokeTimeUtil.now() } returns now

        // when
        syncCatalogUseCase(minInterval)

        // then
        coVerify(exactly = 1) { scheduler.enqueueDailySync() }
        coVerify(exactly = 0) { scheduler.enqueueImmediateSync() }
    }

    @Test
    fun `should do nothing when elapsed time is less than minInterval`() = runTest {
        // given
        val lastSync = 2_000_000L
        val now = 2_000_000L + 9_999L
        val minInterval = 10_000L
        coEvery { repository.getLastSyncAt() } returns lastSync
        every { pokeTimeUtil.now() } returns now

        // when
        syncCatalogUseCase(minInterval)

        // then
        coVerify(exactly = 0) { scheduler.enqueueImmediateSync() }
        coVerify(exactly = 0) { scheduler.enqueueDailySync() }
    }

    @Test
    fun `should prefer immediate sync branch over others when lastSync is zero`() = runTest {
        // given
        coEvery { repository.getLastSyncAt() } returns 0L
        every { pokeTimeUtil.now() } returns Long.MAX_VALUE
        val minInterval = 1L

        // when
        syncCatalogUseCase(minInterval)

        // then
        coVerify(exactly = 1) { scheduler.enqueueImmediateSync() }
        coVerify(exactly = 0) { scheduler.enqueueDailySync() }
    }
}
