package com.amazonaws.domain

import com.amazonaws.model.AppResult
import com.amazonaws.model.TeamMatchesModel
import javax.inject.Inject

class GetMatchesByTeamsIdUseCase @Inject constructor(private val iMatchesGateway: IMatchesGateway) {
    suspend operator fun invoke(teamId: String): AppResult<TeamMatchesModel>? {
        return iMatchesGateway.getMatchesByTeamId(teamId)
    }
}