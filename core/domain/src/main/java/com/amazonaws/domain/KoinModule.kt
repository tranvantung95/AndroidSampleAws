package com.amazonaws.domain

import org.koin.dsl.module

val useCaseModule = module {
    factory {
        GetTeamsUseCase(get())
    }

    factory { GetMatchesUseCase(get()) }
    factory { GetMatchesByTeamsIdUseCase(get()) }
}