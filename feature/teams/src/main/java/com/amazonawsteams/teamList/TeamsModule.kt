package com.amazonawsteams.teamList

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val teamsViewModelModule = module {
    viewModel {
        TeamsViewModel(get())
    }
}