package com.github.pokemon.pokedex

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.github.pokemon.pokedex.domain.model.PokemonCatalog
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.yield

object PokemonDiffTest : DiffUtil.ItemCallback<PokemonCatalog>() {
    override fun areItemsTheSame(oldItem: PokemonCatalog, newItem: PokemonCatalog) =
        oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: PokemonCatalog, newItem: PokemonCatalog) =
        oldItem == newItem
}

private object NoopListCallback : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}

suspend fun PagingData<PokemonCatalog>.toListUsingDiffer(
    dispatcher: TestDispatcher
): List<PokemonCatalog> {
    val differ = AsyncPagingDataDiffer(
        diffCallback = PokemonDiffTest,
        updateCallback = NoopListCallback,
        workerDispatcher = dispatcher
    )
    differ.submitData(this)
    yield()
    return differ.snapshot().items
}
