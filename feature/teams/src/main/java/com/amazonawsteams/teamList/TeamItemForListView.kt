package com.amazonawsteams.teamList

import androidx.viewbinding.ViewBinding
import com.amazonaws.common.RecyclerViewModelItem
import com.amazonaws.common.asyncText
import com.amazonaws.common.databindingVer.loadImageFromUrl
import com.amazonaws.model.TeamsModel
import com.amazonawsteams.R
import com.amazonawsteams.databinding.ItemTeamBinding

class TeamItemForListView(
    private val teamItem: TeamsModel
): RecyclerViewModelItem<TeamsModel> {
    override fun getDefaultSpanCount(): Int = 1

    override fun getRealModelData(): TeamsModel = teamItem

    override fun itemListGetViewType(): Int = R.layout.item_team

    override fun bindItemDataToRecyclerView(binding: ViewBinding) {
        (binding as? ItemTeamBinding)?.apply {
            imageTeamLogo.loadImageFromUrl(teamItem.logo)
            textTeamName.asyncText(teamItem.name)
        }
    }

    override fun itemListSameWith(otherItem: RecyclerViewModelItem<*>): Boolean {
        val checkType = otherItem is TeamItemForListView
        if (checkType.not()) return false
        return teamItem.id == (otherItem as TeamItemForListView).teamItem.id
    }

    override fun itemListPayloadSameWith(otherItem: RecyclerViewModelItem<*>): Boolean? {
        val checkType = otherItem is TeamItemForListView
        if (checkType.not()) return null
        val otherTeamItem = (otherItem as TeamItemForListView).teamItem
        return teamItem.name == otherTeamItem.name && teamItem.logo == otherTeamItem.logo
    }
}