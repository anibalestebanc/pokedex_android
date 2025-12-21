package com.github.pokemon.pokedex.data.repository

import com.github.pokemon.pokedex.data.datasource.local.SyncCatalogLocalDataSource
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class LocalSyncCatalogRepositoryTest {

    @MockK
    lateinit var localDataSource: SyncCatalogLocalDataSource

    private lateinit var repository: LocalSyncCatalogRepository

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        repository = LocalSyncCatalogRepository(
            localDataSource = localDataSource,
        )
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `should delegate getLastSyncAt to local`() = runTest {
        coEvery { localDataSource.getLastSyncAt() } returns 123L
        assertEquals(123L, repository.getLastSyncAt())
        coVerify { localDataSource.getLastSyncAt() }
    }

    @Test
    fun `should delegate setLastSyncAt to local`() = runTest {
        coEvery { localDataSource.setLastSyncAt(456L) } returns Unit
        repository.setLastSyncAt(456L)
        coVerify { localDataSource.setLastSyncAt(456L) }
    }

}
