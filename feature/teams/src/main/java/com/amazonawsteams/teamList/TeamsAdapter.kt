package com.amazonawsteams.teamList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.amazonaws.common.BaseListAdapter
import com.amazonaws.common.RecyclerViewHolder
import com.amazonaws.model.TeamsModel
import com.amazonawsteams.R
import com.amazonawsteams.databinding.ItemTeamBinding

class TeamsAdapter(
    private val itemClickCallback: (TeamsModel) -> Unit,
) : BaseListAdapter() {
    override fun inflateNewViewFromXml(parent: ViewGroup, viewType: Int): ViewBinding {
        return ItemTeamBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    }

    override fun bindingInflatedViewInPool(inflatedView: View, viewType: Int): ViewBinding {
        return ItemTeamBinding.bind(inflatedView)
    }

    override fun layoutResWithPredictNumberOnScreen(): List<Pair<Int, Int>> = listOf(
        Pair(R.layout.item_team, 3)
    )

    override fun onSetItemClickAction(holder: RecyclerViewHolder, viewType: Int) {
        holder.binding.root.setOnClickListener {
            (getCurrentRealDataItem(holder) as? TeamsModel)?.let(itemClickCallback)
        }
    }
}