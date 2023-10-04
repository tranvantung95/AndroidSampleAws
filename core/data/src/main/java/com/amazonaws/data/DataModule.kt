package com.amazonaws.data

import com.amazonaws.domain.IMatchesGateway
import com.amazonaws.domain.ITeamsGateway
import org.koin.dsl.module

val dataModule = module {
    factory {
        MatchesRepository(get())
    }
    factory {
        TeamsRepository(get())
    }
    single<ITeamsGateway> {
        TeamsRepository(get())
    }
    single<IMatchesGateway> {
        MatchesRepository(get())
    }
}