package com.github.zsoltk.pokedex.domain.repository

import androidx.paging.PagingData
import com.github.zsoltk.pokedex.domain.model.PokemonCatalog
import kotlinx.coroutines.flow.Flow

interface PokemonSearchRepository {
    fun searchPaged(query: String?): Flow<PagingData<PokemonCatalog>>
}
