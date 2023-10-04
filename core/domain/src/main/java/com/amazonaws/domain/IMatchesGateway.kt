package com.amazonaws.domain

import com.amazonaws.model.AppResult
import com.amazonaws.model.TeamMatchesModel

interface IMatchesGateway {
    suspend fun getMatches(): AppResult<TeamMatchesModel>?
    suspend fun getMatchesByTeamId(id: String): AppResult<TeamMatchesModel>?

}