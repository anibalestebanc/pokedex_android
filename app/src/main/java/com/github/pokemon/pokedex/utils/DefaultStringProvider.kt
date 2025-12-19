package com.github.pokemon.pokedex.utils

import android.content.Context
import androidx.annotation.StringRes

interface StringProvider {
    operator fun invoke(@StringRes restId: Int): String
}

class DefaultStringProvider(private val context: Context) : StringProvider {
    override fun invoke(@StringRes restId: Int): String = context.getString(restId)
}
