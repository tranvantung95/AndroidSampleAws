package com.amazonaws.matches.matchesList

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val matchesViewModelModule = module {
    viewModel {
        MatchesViewModel(get(), get())
    }
}