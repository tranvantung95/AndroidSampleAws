package com.amazonaws.matches.matchesList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amazonaws.common.RetryChannel
import com.amazonaws.common.fromFlowToStateFlow
import com.amazonaws.domain.GetMatchesByTeamsIdUseCase
import com.amazonaws.domain.GetMatchesUseCase
import com.amazonaws.model.AppResult
import com.amazonaws.model.UiError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchesViewModel @Inject constructor(
    private val getMatchesUseCase: GetMatchesUseCase,
    private val getMatchesByTeamsIdUseCase: GetMatchesByTeamsIdUseCase
) : ViewModel() {
    private var teamId: String? = null
    private val retryChannel = RetryChannel()
    private val _matchesState: StateFlow<MatchesUiState?> =
        retryChannel.transformLatest {
            getMatchUseCaseSource().map {
                when (it) {
                    is AppResult.Success -> {
                        MatchesUiState.Success(
                            matches = it.data
                        )
                    }

                    else -> {
                        val error = it as AppResult.Error
                        MatchesUiState.LoadFailed(UiError(message = error.error.message))
                    }
                }
            }
        }.fromFlowToStateFlow(viewModelScope, MatchesUiState.Loading)

    val matchesState: StateFlow<MatchesUiState?> = _matchesState

    fun setMatchesFilterType(teamId: String?) {
        this.teamId = teamId
    }


    fun reloadTeamById() {
        viewModelScope.launch {
            retryChannel.retry()
        }
    }

    fun hideBackView(): Boolean {
        return teamId.isNullOrEmpty()
    }

    private fun getMatchUseCaseSource() =
        flow {
            emit(
                if (teamId != null) {
                    getMatchesByTeamsIdUseCase.invoke(teamId!!)
                } else {
                    getMatchesUseCase.invoke()
                }
            )
        }
}