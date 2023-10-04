package com.amazonaws.data

import com.amazonaws.model.AppResult
import com.amazonaws.model.TeamMatchesModel
import com.amazonaws.model.TeamsModel
import com.amazonaws.network.model.teams.TeamsResponse
import com.amazonaws.testing.BaseRepositoryTest
import com.amazonaws.testing.data.TestData
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import java.net.HttpURLConnection


class TeamsRepositoryTest : BaseRepositoryTest() {

    private lateinit var teamsRepository: TeamsRepository
    override fun setUp() {
        super.setUp()
        teamsRepository = TeamsRepository(apiService).apply {
            dispatcher = mainCoroutineRule.testDispatcher
        }
    }

    @Test
    fun `test case get teams success`() = runTest {
        callApi(
            {
                teamsRepository.getTeams()
            },
            HttpURLConnection.HTTP_OK,
            TestData.getTeamsResponseAsString(), {
                Assert.assertTrue(it is AppResult.Success)
                Assert.assertEquals(
                    (it as? AppResult.Success<List<TeamsModel>>)?.data?.first()?.name,
                    "Team Red Dragons"
                )
            }
        )
    }

    @Test
    fun `test case get teams fail`() = runTest {
        callApi(
            {
                teamsRepository.getTeams()
            },
            HttpURLConnection.HTTP_BAD_GATEWAY,
            TestData.getTeamsResponseAsString(), {
                Assert.assertTrue(it is AppResult.Error)
            }
        )
    }
}