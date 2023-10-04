package com.amazonaws.matches.matchesList

import com.amazonaws.domain.GetMatchesByTeamsIdUseCase
import com.amazonaws.domain.GetMatchesUseCase
import com.amazonaws.model.AppResult
import com.amazonaws.model.TeamMatchesModel
import com.amazonaws.testing.BaseUnitTest
import com.amazonaws.testing.data.TestData
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test


class MatchesModelUnitTest : BaseUnitTest() {

    @MockK
    lateinit var getMatchesUseCase: GetMatchesUseCase

    @MockK
    lateinit var getMatchesByTeamsIdUseCase: GetMatchesByTeamsIdUseCase
    lateinit var viewModel: MatchesViewModel
    override fun setUp() {
        super.setUp()
        viewModel = MatchesViewModel(getMatchesUseCase, getMatchesByTeamsIdUseCase)
    }

    @Test
    fun `test case load matches is success`() = runTest {
        coEvery {
            getMatchesUseCase.invoke()
        } answers {
            AppResult.Success(TestData.mockMatchesData())
        }
        val listResult = mutableListOf<MatchesUiState>()
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.matchesState.collectLatest {
                listResult.add(it!!)
            }
        }
        Assert.assertTrue(listResult[0] is MatchesUiState.Loading)
        Assert.assertEquals(
            (listResult[1] as MatchesUiState.Success).matches?.previous?.first()?.home,
            "Team Cool Eagles"
        )
        collectJob.cancel()
    }

    @Test
    fun `test case load matches is fail`() = runTest {
        coEvery {
            getMatchesUseCase.invoke()
        } answers {
            AppResult.Error(Throwable("no connection"))
        }
        val listResult = mutableListOf<MatchesUiState>()
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.matchesState.collectLatest {
                listResult.add(it!!)
            }
        }
        Assert.assertTrue(listResult[0] is MatchesUiState.Loading)
        Assert.assertEquals(
            (listResult[1] as MatchesUiState.LoadFailed).error.message,
            "no connection"
        )
        collectJob.cancel()
    }

    @Test
    fun `test case load matches is empty`() = runTest {
        coEvery {
            getMatchesUseCase.invoke()
        } answers {
            AppResult.Success(TeamMatchesModel())
        }
        val listResult = mutableListOf<MatchesUiState>()
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.matchesState.collectLatest {
                listResult.add(it!!)
            }
        }
        Assert.assertTrue(listResult[0] is MatchesUiState.Loading)
        Assert.assertEquals((listResult[1] as MatchesUiState.Success).matches?.previous, null)
        Assert.assertEquals((listResult[1] as MatchesUiState.Success).matches?.upComing, null)
        collectJob.cancel()
    }

    @Test
    fun `test case get matches by id success`() = runTest {
        viewModel.setMatchesFilterType("teamID")
        coEvery {
            getMatchesByTeamsIdUseCase.invoke(any())
        } answers {
            AppResult.Success(TestData.mockMatchesData())
        }
        val listResult = mutableListOf<MatchesUiState>()
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.matchesState.collectLatest {
                listResult.add(it!!)
            }
        }
        Assert.assertTrue(listResult[0] is MatchesUiState.Loading)
        Assert.assertEquals(
            (listResult[1] as MatchesUiState.Success).matches?.previous?.first()?.home,
            "Team Cool Eagles"
        )
        collectJob.cancel()
    }
    @Test
    fun `test case get matches by id fail`() = runTest {
        viewModel.setMatchesFilterType("teamID")
        coEvery {
            getMatchesByTeamsIdUseCase.invoke(any())
        } answers {
            AppResult.Error(Throwable("no connection"))
        }
        val listResult = mutableListOf<MatchesUiState>()
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.matchesState.collectLatest {
                listResult.add(it!!)
            }
        }
        Assert.assertTrue(listResult[0] is MatchesUiState.Loading)
        Assert.assertEquals(
            (listResult[1] as MatchesUiState.LoadFailed).error?.message,
            "no connection"
        )
        collectJob.cancel()
    }
}