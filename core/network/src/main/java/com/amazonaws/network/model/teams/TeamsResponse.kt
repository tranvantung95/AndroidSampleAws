package com.amazonaws.network.model.teams

import com.amazonaws.model.TeamsModel as teamModel

data class TeamsResponse(
    val teams: MutableList<Team>?
)

fun TeamsResponse.asTeams(): List<teamModel> {
    return this.teams?.mapNotNull { it ->
        teamModel(it.id, it.name, it.logo)
    } ?: listOf()
}