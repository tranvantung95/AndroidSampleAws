package com.amazonaws.matches.matchesList

import com.amazonaws.model.TeamMatchesModel
import com.amazonaws.model.UiError

sealed interface MatchesUiState {
    data object Loading : MatchesUiState
    data object EmptyList : MatchesUiState
    data class LoadFailed(val error: UiError) : MatchesUiState
    data class Success(val matches: TeamMatchesModel?) : MatchesUiState
}
