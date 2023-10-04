package com.amazonawsteams.screen

import android.view.View
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import org.hamcrest.Matcher
import com.amazonawsteams.R
import io.github.kakaocup.kakao.text.KTextView

object TeamsFragmentScreen : KScreen<TeamsFragmentScreen>() {
    override val layoutId: Int? = null

    override val viewClass: Class<*>? = null

    val tvEmpty = KTextView {
        withId(R.id.tvEmpty)
    }
    val rcTeams = KRecyclerView(builder = {
        withId(R.id.rcTeams)
    }, itemTypeBuilder = {
        itemType {
            TeamsItems(it)
        }
    })

    class TeamsItems(matcher: Matcher<View>) : KRecyclerItem<TeamsItems>(matcher) {

    }
}