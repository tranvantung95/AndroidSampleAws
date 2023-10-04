package com.amazonaws.network.model.matches

import com.google.gson.annotations.SerializedName

data class MatchesDetailResponse(
    @SerializedName("previous")
    var previousResponses: ArrayList<PreviousResponse> = arrayListOf(),
    @SerializedName("upcoming")
    var upcomingResponse: ArrayList<UpcomingResponse> = arrayListOf()
)
