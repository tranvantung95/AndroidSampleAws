package com.amazonaws.matches.matchesList

import androidx.viewbinding.ViewBinding
import com.amazonaws.common.RecyclerViewModelItem
import com.amazonaws.common.asyncText
import com.amazonaws.common.formatTime
import com.amazonaws.common.goneIf
import com.amazonaws.matches.R
import com.amazonaws.matches.databinding.ItemMatchesBinding
import com.amazonaws.matches.matchesList.matchesTab.MatchesType
import com.amazonaws.model.MatchesModel

class MatchesItemForListView(
    private val match: MatchesModel,
    private val matchesType: MatchesType
) : RecyclerViewModelItem<MatchesModel> {
    override fun getDefaultSpanCount(): Int = 1

    override fun getRealModelData(): MatchesModel = match

    override fun itemListGetViewType(): Int = R.layout.item_matches

    override fun bindItemDataToRecyclerView(binding: ViewBinding) {
        (binding as? ItemMatchesBinding)?.apply {
            textHomeTeam.asyncText(match.home)
            textAwayTeam.asyncText(match.away)
            textWinnerTeam.asyncText(match.winner)
            textMatchDes.asyncText(match.description)
            textMatchDate.asyncText(match.date?.let { formatTime(it) })
            val isInPreviousMatchesMode = matchesType == MatchesType.PREVIOUS
            buttonHighlight.goneIf(!isInPreviousMatchesMode)
            textWinnerTeam.goneIf(!isInPreviousMatchesMode)
            imageSchedule.goneIf(isInPreviousMatchesMode)
        }
    }

    override fun itemListSameWith(otherItem: RecyclerViewModelItem<*>): Boolean {
        val checkType = otherItem is MatchesItemForListView
        if (checkType.not()) return false
        return match.compareWith((otherItem as MatchesItemForListView).match)
    }

    override fun itemListPayloadSameWith(otherItem: RecyclerViewModelItem<*>): Boolean? {
        val checkType = otherItem is MatchesItemForListView
        if (checkType.not()) return null
        return match.compareWith((otherItem as MatchesItemForListView).match)
    }
}