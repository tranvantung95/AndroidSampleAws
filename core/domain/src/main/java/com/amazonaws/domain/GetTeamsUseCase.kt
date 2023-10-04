package com.amazonaws.domain

import com.amazonaws.model.AppResult
import com.amazonaws.model.TeamsModel

class GetTeamsUseCase(private val gateway: ITeamsGateway) {
    suspend operator fun invoke(): AppResult<List<TeamsModel>>? {
        return gateway.getTeams()
    }
}