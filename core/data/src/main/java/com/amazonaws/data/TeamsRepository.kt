package com.amazonaws.data

import com.amazonaws.domain.ITeamsGateway
import com.amazonaws.model.AppResult
import com.amazonaws.model.TeamsModel
import com.amazonaws.network.APIInterface
import com.amazonaws.network.model.teams.asTeams
import com.amazonaws.network.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher

class TeamsRepository(private val apiInterface: APIInterface) : ITeamsGateway {
    var dispatcher: CoroutineDispatcher? = null
    override suspend fun getTeams(): AppResult<List<TeamsModel>>? {
        return safeApiCall(dispatcher) {
            apiInterface.getTeams().asTeams()
        }
    }
}