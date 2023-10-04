package com.amazonaws.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class MatchesModel(
    val home: String? = null,
    val away: String? = null,
    val date: String? = null,
    val description: String? = null,
    val winner: String? = null,
    val highlights: String? = null
) : Parcelable, Serializable {
    fun compareWith(newMatchesModel: MatchesModel): Boolean {
        return home == newMatchesModel.home && away == newMatchesModel.away && date == newMatchesModel.date
    }
}