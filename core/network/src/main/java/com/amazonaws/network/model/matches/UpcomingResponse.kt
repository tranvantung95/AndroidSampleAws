package com.amazonaws.network.model.matches

import com.amazonaws.model.MatchesModel
import com.google.gson.annotations.SerializedName

data class UpcomingResponse(
    @SerializedName("date")
    var date: String? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("home")
    var home: String? = null,
    @SerializedName("away")
    var away: String? = null
)

fun UpcomingResponse.asTeamMatch(): MatchesModel {
    return MatchesModel(
        home, away, date, description
    )
}