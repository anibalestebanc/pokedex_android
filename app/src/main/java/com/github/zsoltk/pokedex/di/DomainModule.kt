package com.github.zsoltk.pokedex.di

import com.github.zsoltk.pokedex.domain.usecase.EnqueueDailySyncCatalogUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    //UseCase
    factoryOf(::EnqueueDailySyncCatalogUseCase)
}
