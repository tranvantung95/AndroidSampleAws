package com.amazonaws.model


data class TeamMatchesModel(
    val previous: List<MatchesModel>? = null,
    val upComing: List<MatchesModel>? = null
)
