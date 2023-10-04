package com.amazonaws.domain

import com.amazonaws.model.AppResult
import com.amazonaws.model.TeamMatchesModel

class GetMatchesUseCase(private val iMatchesGateway: IMatchesGateway) {

    suspend operator fun invoke(): AppResult<TeamMatchesModel>? {
        return iMatchesGateway.getMatches()
    }
}