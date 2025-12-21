package com.github.pokemon.pokedex.utils

import android.content.Context
import androidx.annotation.StringRes

interface StringProvider {
    operator fun invoke(@StringRes restId: Int, vararg format: Any): String
}

class DefaultStringProvider(private val context: Context) : StringProvider {
    override fun invoke(@StringRes restId: Int, vararg format: Any): String =
        context.getString(restId, *format)
}
