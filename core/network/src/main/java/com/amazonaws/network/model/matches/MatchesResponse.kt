package com.amazonaws.network.model.matches

import com.google.gson.annotations.SerializedName

data class MatchesResponse(
    @SerializedName("matches")
    var matchesDetailResponse: MatchesDetailResponse? = null)
