package com.github.pokemon.pokedex.domain.usecase

import com.github.pokemon.pokedex.domain.model.PokemonDetail
import com.github.pokemon.pokedex.domain.repository.PokemonDetailRepository
import com.github.pokemon.pokedex.utils.RefreshDueUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart

class ObserveAndRefreshDetailUseCase(
    private val repository: PokemonDetailRepository,
    private val refreshDueUtil: RefreshDueUtil,
) {
    operator fun invoke(id: Int): Flow<PokemonDetail?> {
        return repository.observePokemonDetail(id).onStart {
            val lastUpdated = repository.getLastUpdated(id)
            if (refreshDueUtil.isRefreshDue(lastUpdated)) {
                runCatching { repository.refreshDetail(id) }
            }
        }
    }
}
