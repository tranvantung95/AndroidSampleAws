package com.amazonaws.network.model.matches

import com.amazonaws.model.MatchesModel
import com.google.gson.annotations.SerializedName

data class PreviousResponse(
    @SerializedName("date")
    var date: String? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("home")
    var home: String? = null,
    @SerializedName("away")
    var away: String? = null,
    @SerializedName("winner")
    var winner: String? = null,
    @SerializedName("highlights")
    var highlights: String? = null
)

fun PreviousResponse.asTeamMatch(): MatchesModel {
    return MatchesModel(
        home, away, date, description, winner, highlights
    )
}