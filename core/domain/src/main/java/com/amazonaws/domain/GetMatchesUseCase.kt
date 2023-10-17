package com.amazonaws.domain

import com.amazonaws.model.AppResult
import com.amazonaws.model.TeamMatchesModel
import javax.inject.Inject


class GetMatchesUseCase @Inject constructor(private val iMatchesGateway: IMatchesGateway) {

    suspend operator fun invoke(): AppResult<TeamMatchesModel>? {
        return iMatchesGateway.getMatches()
    }
}