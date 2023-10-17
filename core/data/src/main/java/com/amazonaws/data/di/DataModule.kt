package com.amazonaws.data.di

import com.amazonaws.data.MatchesRepository
import com.amazonaws.data.TeamsRepository
import com.amazonaws.domain.IMatchesGateway
import com.amazonaws.domain.ITeamsGateway
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindMatchesRepository(matchesRepository: MatchesRepository): IMatchesGateway

    @Binds
    fun bindTeamsRepository(teamsRepository: TeamsRepository): ITeamsGateway
}