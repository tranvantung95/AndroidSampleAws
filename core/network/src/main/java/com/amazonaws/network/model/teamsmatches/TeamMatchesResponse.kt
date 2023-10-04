package com.amazonaws.network.model.teamsmatches

import com.amazonaws.model.TeamMatchesModel
import com.amazonaws.network.model.matches.MatchesDetailResponse
import com.amazonaws.network.model.matches.asTeamMatch
import com.google.gson.annotations.SerializedName
import  com.amazonaws.model.MatchesModel as MatchesModel

data class TeamMatchesResponse(
    @SerializedName("matches")
    var matchesDetailResponse: MatchesDetailResponse? = null
)

fun TeamMatchesResponse.asTeamMatches(): TeamMatchesModel {
    return TeamMatchesModel(
        previous = matchesDetailResponse?.previousResponses?.map { it.asTeamMatch() },
        upComing = matchesDetailResponse?.upcomingResponse?.map { it.asTeamMatch() })
}

fun MatchesDetailResponse.asMatches(): MatchesModel {
    return MatchesModel()
}
