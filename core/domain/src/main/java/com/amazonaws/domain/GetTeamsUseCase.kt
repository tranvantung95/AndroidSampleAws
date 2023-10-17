package com.amazonaws.domain

import com.amazonaws.model.AppResult
import com.amazonaws.model.TeamsModel
import javax.inject.Inject

class GetTeamsUseCase @Inject constructor(private val gateway: ITeamsGateway) {
    suspend operator fun invoke(): AppResult<List<TeamsModel>>? {
        return gateway.getTeams()
    }
}