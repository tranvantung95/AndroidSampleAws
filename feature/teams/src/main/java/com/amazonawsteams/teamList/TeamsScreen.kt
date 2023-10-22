package com.amazonawsteams.teamList

import androidx.activity.compose.ReportDrawnWhen

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.amazonaws.designsystem.theme.AppTheme
import com.amazonaws.model.TeamsModel


@Composable
internal fun TeamsRouter(
    onTeamClick: (String) -> Unit,
    viewModel: TeamsViewModel = hiltViewModel()
) {
    val teamsUiState by viewModel.teamState.collectAsStateWithLifecycle()
    TeamsScreen(onTeamClick, Modifier, teamsUiState)
}

@Composable
fun TeamsScreen(
    onTeamClick: (String) -> Unit,
    modifier: Modifier,
    teamsUiState: TeamsUiState
) {
    val isLoading = teamsUiState is TeamsUiState.Loading
    ReportDrawnWhen {
        !isLoading
    }
    when (teamsUiState) {
        TeamsUiState.Loading,
        TeamsUiState.EmptyList,
        -> Unit

        is TeamsUiState.Success -> {
            val teams = teamsUiState.teams
            LazyColumn {
                items(teams.size) {
                    val teams = teams.getOrNull(it)
                    TeamRow(teams, modifier)
                }
            }
        }

        else -> {

        }
    }

}

@Composable
fun TeamRow(teamModel: TeamsModel?, modifier: Modifier) {
    Row(
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
            .padding(bottom = 10.dp),
    ) {
        AsyncImage(
            model = teamModel?.logo.orEmpty(),
            contentDescription = null,
            modifier = Modifier.size(50.dp, 50.dp)
        )
        Text(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
                .wrapContentHeight(),
            text = teamModel?.name.orEmpty(),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Start
        )
    }
}

@Preview()
@Composable
fun TeamsRowPreview() {
    BoxWithConstraints {
        AppTheme {
            TeamsScreen(
                onTeamClick = {
                },
                modifier = Modifier,
                teamsUiState = TeamsUiState.Success(
                    teams = listOf(TeamsModel("1", "Teams a", "https://picsum.photos/200/300"))
                )
            )
        }
    }
}
