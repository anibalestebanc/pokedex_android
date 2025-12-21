package com.github.pokemon.pokedex.di

import com.github.pokemon.pokedex.utils.DefaultLoggerError
import com.github.pokemon.pokedex.utils.LoggerError
import com.github.pokemon.pokedex.core.work.SyncPokemonCatalogWorkScheduler
import com.github.pokemon.pokedex.utils.DefaultPokeTimeUtil
import com.github.pokemon.pokedex.utils.DefaultRefreshDueUtil
import com.github.pokemon.pokedex.utils.DefaultStringProvider
import com.github.pokemon.pokedex.utils.PokeTimeUtil
import com.github.pokemon.pokedex.utils.RefreshDueUtil
import com.github.pokemon.pokedex.utils.StringProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    single { SyncPokemonCatalogWorkScheduler(androidContext()) }

    single<StringProvider> { DefaultStringProvider(androidContext()) }

    single<LoggerError> { DefaultLoggerError() }

    single<PokeTimeUtil> { DefaultPokeTimeUtil() }

    single<RefreshDueUtil> { DefaultRefreshDueUtil(get()) }
}
