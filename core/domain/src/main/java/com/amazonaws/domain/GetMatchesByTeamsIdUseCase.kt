package com.amazonaws.domain

import com.amazonaws.model.AppResult
import com.amazonaws.model.TeamMatchesModel

class GetMatchesByTeamsIdUseCase(private val iMatchesGateway: IMatchesGateway) {
    suspend operator fun invoke(teamId: String): AppResult<TeamMatchesModel>? {
        return iMatchesGateway.getMatchesByTeamId(teamId)
    }
}