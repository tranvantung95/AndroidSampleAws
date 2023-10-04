package com.amazonawsteams.teamList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amazonaws.domain.GetTeamsUseCase
import com.amazonaws.model.AppResult
import com.amazonaws.model.UiError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class TeamsViewModel(private val teamsUseCase: GetTeamsUseCase) : ViewModel() {
    var dispatcher: CoroutineDispatcher? = null
    private val _teamsState = MutableStateFlow<TeamsUiState>(TeamsUiState.Loading).map {
        when (val teams = teamsUseCase.invoke()) {
            is AppResult.Success -> {
                if (teams.data?.isEmpty() == true) {
                    TeamsUiState.EmptyList
                } else {
                    TeamsUiState.Success(
                        teams = teams.data?.map { TeamItemForListView(it) } ?: listOf()
                    )
                }
            }

            else -> {
                val error = teams as AppResult.Error
                TeamsUiState.LoadFailed(UiError(message = error.error.message))
            }
        }
    }.stateIn(
        dispatcher?.let {
            CoroutineScope(it)
        } ?: kotlin.run {
            viewModelScope
        },
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = TeamsUiState.Loading
    )
    val teamState: StateFlow<TeamsUiState> = _teamsState

}
