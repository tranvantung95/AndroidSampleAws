package com.amazonaws.domain

import com.amazonaws.model.AppResult
import com.amazonaws.model.TeamsModel

interface ITeamsGateway {
  suspend  fun getTeams(): AppResult<List<TeamsModel>>?
}