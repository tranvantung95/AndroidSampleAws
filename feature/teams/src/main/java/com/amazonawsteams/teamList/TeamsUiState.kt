package com.amazonawsteams.teamList

import com.amazonaws.model.UiError

sealed interface TeamsUiState {
    data object Loading : TeamsUiState
    data object EmptyList : TeamsUiState
    data class LoadFailed(val error: UiError) : TeamsUiState
    data class Success(val teams: List<TeamItemForListView>) : TeamsUiState
}

