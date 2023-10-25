package com.amazonaws.matches

import androidx.activity.compose.ReportDrawnWhen
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amazonaws.designsystem.theme.AppTheme
import com.amazonaws.matches.matchesList.MatchesUiState
import com.amazonaws.matches.matchesList.MatchesViewModel
import kotlinx.coroutines.launch


@Composable
internal fun MatchesRouter(
    onHighlightClick: (String) -> Unit,
    viewModel: MatchesViewModel = hiltViewModel(),
    teamId: String? = null,
    onDetailClick: () -> Unit
) {
    viewModel.setMatchesFilterType(teamId)
    val matchesUiState by viewModel.matchesState.collectAsStateWithLifecycle()
    MatchesScreen(onHighlightClick, Modifier, matchesUiState, onDetailClick)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MatchesScreen(
    onHighlightClick: (String) -> Unit,
    modifier: Modifier,
    matchesUiState: MatchesUiState?,
    onDetailClick: () -> Unit
) {
    val isLoading = matchesUiState is MatchesUiState.Loading
    ReportDrawnWhen {
        !isLoading
    }
    when (matchesUiState) {
        MatchesUiState.Loading, MatchesUiState.EmptyList -> Unit
        is MatchesUiState.Success -> {
            val tabData = listOf(MatchesTabName.Previous, MatchesTabName.Upcoming)
            val pageState = rememberPagerState(initialPage = 0, initialPageOffsetFraction = 0f)
            val tabIndex = pageState.currentPage
            val coroutineScope = rememberCoroutineScope()
            Column {
                TabRow(
                    selectedTabIndex = tabIndex, modifier = Modifier
                ) {
                    tabData.forEachIndexed { index, matchesText ->
                        Tab(selected = index == tabIndex, onClick = {
                            coroutineScope.launch {
                                pageState.animateScrollToPage(index)
                            }
                        }, text = {
                            Text(matchesText.name)
                        })
                    }
                }
                HorizontalPager(state = pageState, pageCount = tabData.size) { index ->
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        MatchesTab(tabData[index], matchesUiState, modifier, { videoUrl ->
                            onHighlightClick.invoke(videoUrl)
                        }, {

                        }, onDetailClick = onDetailClick)
                    }
                }
            }
        }

        else -> {

        }
    }
}

enum class MatchesTabName {
    Previous, Upcoming
}

@Preview
@Composable
fun PreviewMatchesTablayout() {
    BoxWithConstraints {
        AppTheme {
            MatchesScreen(
                onHighlightClick = {

                },
                modifier = Modifier,
                matchesUiState = MatchesUiState.Success(null), {

                }
            )
        }
    }
}