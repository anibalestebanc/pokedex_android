package com.github.zsoltk.pokedex.ui.components

sealed class AsyncState<T> {
    class Initialised<T>: AsyncState<T>()
    class Loading<T>: AsyncState<T>()
    data class Error<T>(val error: Throwable): AsyncState<T>()
    data class Result<T>(val result: T): AsyncState<T>()
}
