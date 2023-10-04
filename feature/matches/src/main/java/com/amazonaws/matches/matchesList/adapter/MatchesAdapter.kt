package com.amazonaws.matches.matchesList.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.amazonaws.common.BaseListAdapter
import com.amazonaws.common.RecyclerViewHolder
import com.amazonaws.matches.R
import com.amazonaws.matches.databinding.ItemMatchesBinding
import com.amazonaws.model.MatchesModel

class MatchesAdapter(
    private val onHighlightsClickCallback: (MatchesModel) -> Unit,
    private val onScheduleClickCallback: (MatchesModel) -> Unit,
) : BaseListAdapter() {

    override fun inflateNewViewFromXml(parent: ViewGroup, viewType: Int): ViewBinding {
        return ItemMatchesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    }

    override fun bindingInflatedViewInPool(inflatedView: View, viewType: Int): ViewBinding {
        return ItemMatchesBinding.bind(inflatedView)
    }

    override fun layoutResWithPredictNumberOnScreen(): List<Pair<Int, Int>> = listOf(
        Pair(R.layout.item_matches, 5)
    )

    override fun onSetItemClickAction(holder: RecyclerViewHolder, viewType: Int) {
        (holder.binding as? ItemMatchesBinding)?.apply {
            buttonHighlight.setOnClickListener {
                (getCurrentRealDataItem(holder) as? MatchesModel)?.let(onHighlightsClickCallback)
            }
            imageSchedule.setOnClickListener {
                (getCurrentRealDataItem(holder) as? MatchesModel)?.let(onScheduleClickCallback)
            }
        }
    }
}