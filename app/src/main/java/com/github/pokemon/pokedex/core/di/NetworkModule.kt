package com.github.pokemon.pokedex.core.di

import android.app.Application
import com.github.pokemon.pokedex.core.network.BaseUrlProvider
import com.github.pokemon.pokedex.core.network.RetrofitFactory
import com.github.pokemon.pokedex.core.network.utils.NetworkMonitor
import com.github.pokemon.pokedex.core.network.utils.OnlineNetworkMonitor
import com.github.pokemon.pokedex.data.datasource.remote.api.PokemonApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val networkModule = module {

    single<String>(named("default_base_url")) { BaseUrlProvider.getBaseUrl() }

    // Todo use -> named("io_dispatcher")
    single<CoroutineDispatcher> { Dispatchers.IO }

    single<NetworkMonitor> {
        OnlineNetworkMonitor(
            context = get<Application>(),
        )
    }

    //Api
    factory<PokemonApi> {
        RetrofitFactory.createService(
            baseUrl = get(qualifier = named("default_base_url")),
            klass = PokemonApi::class.java,
        )
    }
}
