package com.github.pokemon.pokedex.di

import com.github.pokemon.pokedex.utils.DefaultLoggerError
import com.github.pokemon.pokedex.utils.LoggerError
import com.github.pokemon.pokedex.utils.DefaultErrorMapper
import com.github.pokemon.pokedex.utils.DefaultPokeTimeUtil
import com.github.pokemon.pokedex.utils.DefaultRefreshDueUtil
import com.github.pokemon.pokedex.utils.DefaultStringProvider
import com.github.pokemon.pokedex.utils.ErrorMapper
import com.github.pokemon.pokedex.utils.PokeTimeUtil
import com.github.pokemon.pokedex.utils.RefreshDueUtil
import com.github.pokemon.pokedex.utils.StringProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    single<StringProvider> { DefaultStringProvider(androidContext()) }

    single<LoggerError> { DefaultLoggerError() }

    single<PokeTimeUtil> { DefaultPokeTimeUtil() }

    single<RefreshDueUtil> { DefaultRefreshDueUtil(get()) }

    single<ErrorMapper> { DefaultErrorMapper(get()) }
}
