package com.amazonaws.matches

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amazonaws.designsystem.theme.AppTheme
import com.amazonaws.matches.matchesList.MatchesUiState
import com.amazonaws.model.MatchesModel
import com.amazonaws.model.TeamMatchesModel

@Composable
fun MatchesTab(
    matchesTabName: MatchesTabName,
    matchesUiState: MatchesUiState.Success,
    modifier: Modifier,
    onHighlightClick: (String) -> Unit,
    onScheduleClick: (String) -> Unit,
    onDetailClick: () -> Unit
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        if (matchesTabName == MatchesTabName.Previous) {
            items(matchesUiState.matches?.previous?.size ?: 0) { index ->
                MatchesRow(
                    matchesModel = matchesUiState.matches?.previous?.get(index),
                    modifier = modifier, { it ->
                        onHighlightClick.invoke(it)
                    }, {

                    }, onDetailClick = onDetailClick
                )
            }
        } else {
            items(matchesUiState.matches?.upComing?.size ?: 0) { index ->
                MatchesRow(
                    matchesModel = matchesUiState.matches?.upComing?.get(index),
                    modifier = modifier, {

                    }, { it ->
                        onScheduleClick.invoke(it)
                    }, onDetailClick = onDetailClick
                )
            }
        }

    }
}

@Composable
fun MatchesRow(
    matchesModel: MatchesModel?, modifier: Modifier, onHighlightClick: (String) -> Unit,
    onScheduleClick: (String) -> Unit, onDetailClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable {
                onDetailClick.invoke()
            },
        shape = RoundedCornerShape(4.dp)
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = matchesModel?.home.orEmpty(),
                modifier = modifier
                    .wrapContentHeight()
                    .wrapContentWidth(),
                style = MaterialTheme.typography.titleLarge
            )
            Text(text = " - ")
            Text(
                text = matchesModel?.away.orEmpty(),
                modifier = modifier
                    .wrapContentHeight()
                    .wrapContentWidth(),
                style = MaterialTheme.typography.titleLarge
            )
        }
        if (matchesModel?.winner?.isEmpty() == false) {
            Text(
                text = "Winner: ${matchesModel.winner.orEmpty()}",
                style = MaterialTheme.typography.titleMedium
            )
        }
        Spacer(modifier = modifier.height(5.dp))
        Text(text = matchesModel?.description.orEmpty())
        Spacer(modifier = modifier.height(5.dp))
        Text(text = matchesModel?.date.orEmpty())
        Spacer(modifier = modifier.height(5.dp))
        if (matchesModel?.winner?.isNullOrEmpty() == false) {
            Button(onClick = { onHighlightClick.invoke(matchesModel.highlights.orEmpty()) }) {
                Text(text = stringResource(id = R.string.text_highlight))
            }
        } else {
            Image(
                painter = painterResource(id = com.amazonaws.common.R.drawable.ic_add_alarm_24),
                contentDescription = " add alarm"
            )
        }
        Spacer(modifier = modifier.height(5.dp))
    }
}

@Preview
@Composable
fun PreviewPreviousMatchesRow() {
    MatchesRow(
        matchesModel = MatchesModel(
            "Home",
            "Away",
            winner = "Home",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            date = "11/11/2023"
        ), modifier = Modifier, {

        }, {

        },{

        }
    )
}

@Preview
@Composable
fun PreviewUpCommingMatchesRow() {
    MatchesRow(
        matchesModel = MatchesModel(
            "Home",
            "Away",
            winner = "",
            description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            date = "11/11/2023"
        ), modifier = Modifier, {

        }, {

        },{

        }
    )
}

@Preview("PreviewMatchesTab")
@Composable
fun PreviewMatchesTab() {
    BoxWithConstraints {
        AppTheme {
            MatchesTab(
                MatchesTabName.Previous,
                matchesUiState = MatchesUiState.Success(TeamMatchesModel()),
                Modifier, {

                }, {

                },{

                }
            )
        }
    }
}