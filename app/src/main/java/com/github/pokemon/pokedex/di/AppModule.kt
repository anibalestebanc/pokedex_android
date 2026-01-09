package com.github.pokemon.pokedex.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.github.pokemon.pokedex.data.datasource.local.DataStoreSyncMetaStore
import com.github.pokemon.pokedex.data.datasource.local.SyncMetaStore
import com.github.pokemon.pokedex.data.datasource.local.syncDataStore
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
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    single<StringProvider> { DefaultStringProvider(androidContext()) }

    single<LoggerError> { DefaultLoggerError() }

    single<PokeTimeUtil> { DefaultPokeTimeUtil() }

    single<RefreshDueUtil> { DefaultRefreshDueUtil(get()) }

    single<ErrorMapper> { DefaultErrorMapper(get()) }

    single<DataStore<Preferences>>(named("sync_metadata")) {
        androidContext().syncDataStore
    }

    //MetaStore
    single<SyncMetaStore> {
        DataStoreSyncMetaStore(get(named("sync_metadata")))
    }

}
