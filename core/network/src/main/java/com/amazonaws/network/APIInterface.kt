package com.amazonaws.network


import com.amazonaws.network.model.teams.TeamsResponse
import com.amazonaws.network.model.teamsmatches.TeamMatchesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIInterface {
    @GET("/teams")
    suspend fun getTeams(): TeamsResponse

    @GET("/teams/matches")
    suspend fun getMatches(): TeamMatchesResponse

    @GET("/teams/{id}/matches")
    suspend fun getTeamsMatches(@Path("id") teamsId: String): TeamMatchesResponse
}