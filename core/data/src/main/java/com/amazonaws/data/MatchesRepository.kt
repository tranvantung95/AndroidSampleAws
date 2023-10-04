package com.amazonaws.data

import com.amazonaws.domain.IMatchesGateway
import com.amazonaws.model.AppResult
import com.amazonaws.model.TeamMatchesModel
import com.amazonaws.network.APIInterface
import com.amazonaws.network.model.teamsmatches.asTeamMatches
import com.amazonaws.network.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher

class MatchesRepository(private val apiService: APIInterface) : IMatchesGateway {
    var dispatcher: CoroutineDispatcher? = null
    override suspend fun getMatches(): AppResult<TeamMatchesModel>? {
        return safeApiCall(dispatcher) {
            apiService.getMatches().asTeamMatches()
        }
    }

    override suspend fun getMatchesByTeamId(id: String): AppResult<TeamMatchesModel>? {
        return safeApiCall(dispatcher) {
            apiService.getTeamsMatches(id).asTeamMatches()
        }
    }
}