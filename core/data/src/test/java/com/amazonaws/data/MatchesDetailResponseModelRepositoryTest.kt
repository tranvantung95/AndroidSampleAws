package com.amazonaws.data

import com.amazonaws.model.AppResult
import com.amazonaws.model.TeamMatchesModel
import com.amazonaws.testing.BaseRepositoryTest
import com.amazonaws.testing.data.TestData
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import java.net.HttpURLConnection


class MatchesDetailResponseModelRepositoryTest : BaseRepositoryTest() {

    private lateinit var matchesRepository: MatchesRepository
    override fun setUp() {
        super.setUp()
        matchesRepository = MatchesRepository(apiService).apply {
            dispatcher = mainCoroutineRule.testDispatcher
        }
    }

    @Test
    fun `test case get matches success`() = runTest {
        callApi(
            {
                matchesRepository.getMatches()
            },
            HttpURLConnection.HTTP_OK,
            TestData.getMatchesResponseAsString(), {
                Assert.assertTrue(it is AppResult.Success)
            }
        )
    }

    @Test
    fun `test case get matches fail`() = runTest {
        callApi(
            {
                matchesRepository.getMatches()
            },
            HttpURLConnection.HTTP_BAD_GATEWAY,
            TestData.getMatchesResponseAsString(), {
                Assert.assertTrue(it is AppResult.Error)
            }
        )
    }

    @Test
    fun `test case get matches by id success`() = runTest {
        callApi(
            {
                matchesRepository.getMatchesByTeamId("767ec50c-7fdb-4c3d-98f9-d6727ef8252b")
            },
            HttpURLConnection.HTTP_OK,
            TestData.getMatchesResponseAsString(), {
                Assert.assertTrue(it is AppResult.Success)
                Assert.assertEquals(
                    (it as? AppResult.Success<TeamMatchesModel>)?.data?.previous?.first()?.home,
                    "Team Cool Eagles"
                )
            }
        )
    }

    @Test
    fun `test case get matches by id  fail`() = runTest {
        callApi(
            {
                matchesRepository.getMatchesByTeamId("767ec50c-7fdb-4c3d-98f9-d6727ef8252b")
            },
            HttpURLConnection.HTTP_BAD_GATEWAY,
            TestData.getMatchesResponseAsString(), {
                Assert.assertTrue(it is AppResult.Error)
            }
        )
    }
}