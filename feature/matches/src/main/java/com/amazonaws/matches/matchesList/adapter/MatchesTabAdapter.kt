package com.amazonaws.matches.matchesList.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.amazonaws.matches.matchesList.matchesTab.MatchTabFragment
import com.amazonaws.matches.matchesList.matchesTab.MatchesType
import com.amazonaws.model.MatchesModel

class MatchesTabAdapter(
    fragment: Fragment,
    private val previousMatches: List<MatchesModel>?,
    private val upcomingMatches: List<MatchesModel>?
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return MatchTabFragment.getFragmentInstance(
            getTabMatches(position),
            getTabMatchesType(position)
        )
    }

    private fun getTabMatches(position: Int) = when (position) {
        0 -> previousMatches
        1 -> upcomingMatches
        else -> emptyList()
    }

    private fun getTabMatchesType(position: Int) = when (position) {
        0 -> MatchesType.PREVIOUS
        else -> MatchesType.UPCOMING
    }
}