package com.amazonaws.matches.screen

import android.view.View
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.tabs.KTabLayout
import com.amazonaws.matches.R
import com.amazonaws.matches.matchesList.matchesTab.MatchesType
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KButton
import org.hamcrest.Matcher

object MatchesScreen : KScreen<MatchesScreen>() {
    override val layoutId: Int? = null
    override val viewClass: Class<*>? = null

    val tabMatches = KTabLayout {
        withId(R.id.matchesTabLayout)
    }
    val previousRecycler = KRecyclerView(
        builder = {
            withTag(MatchesType.PREVIOUS.name)
        },
        itemTypeBuilder = {
            itemType {
                MatchesItem(it)
            }
        }
    )
    val upComingRecycler = KRecyclerView(
        builder = {
            withTag(MatchesType.UPCOMING.name)
        },
        itemTypeBuilder = {
            itemType {
                MatchesItem(it)
            }
        }
    )

    class MatchesItem(matcher: Matcher<View>) : KRecyclerItem<MatchesItem>(matcher) {
        val btHighlights = KButton(matcher) {
            withId(R.id.buttonHighlight)
        }
        val btSchedule = KImageView(matcher) {
            withId(R.id.imageSchedule)
        }
    }


}