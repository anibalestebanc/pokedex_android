package com.github.zsoltk.pokedex.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun <T> TableRenderer(
    cols: Int,
    items: List<T>,
    cellSpacing: Dp,
    cellRenderer: @Composable (Cell<T>) -> Unit
) {
    val chunkedItems = items.withIndex().chunked(cols)

    Column(verticalArrangement = Arrangement.spacedBy(cellSpacing)) {
        chunkedItems.forEachIndexed { rowIndex, rowItems ->
            Row(horizontalArrangement = Arrangement.spacedBy(cellSpacing)) {
                rowItems.forEach { (itemIndex, item) ->
                    val colIndex = itemIndex % cols
                    val cell = Cell(
                        item = item,
                        index = itemIndex,
                        rowIndex = rowIndex,
                        colIndex = colIndex
                    )
                    Box(modifier = Modifier.weight(1f)) {
                        cellRenderer(cell)
                    }
                }
                // Add spacers for the last row if it's not full
                val emptyCells = cols - rowItems.size
                if (emptyCells > 0) {
                    for (i in 0 until emptyCells) {
                        Spacer(Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

data class Cell<T>(
    /**
     * The associated item to be rendered.
     */
    val item: T,

    /**
     * The index of the item in the original list passed to [TableRenderer]
     */
    val index: Int,
    /**
     * The row index in which this table cell is rendered.
     */
    val rowIndex: Int,

    /**
     * The column index in which this table cell is rendered.
     */
    val colIndex: Int
)
