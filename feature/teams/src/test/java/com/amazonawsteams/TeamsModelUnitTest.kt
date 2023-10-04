package com.amazonawsteams

import com.amazonaws.domain.GetTeamsUseCase
import com.amazonaws.model.AppResult
import com.amazonaws.testing.BaseUnitTest
import com.amazonaws.testing.data.TestData
import com.amazonawsteams.teamList.TeamsUiState
import com.amazonawsteams.teamList.TeamsViewModel
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test


class TeamsModelUnitTest : BaseUnitTest() {
    private lateinit var viewModel: TeamsViewModel

    @MockK
    lateinit var getTeamsUseCase: GetTeamsUseCase


    @OptIn(ObsoleteCoroutinesApi::class)
    override fun setUp() {
        super.setUp()
        viewModel = TeamsViewModel(getTeamsUseCase).apply {
            dispatcher = mainCoroutineRule.testDispatcher
        }
    }

    @Test
    fun `get teams success`() = runTest {
        coEvery {
            getTeamsUseCase.invoke()
        } answers {
            AppResult.Success(TestData.mockTeamsData())
        }
        val listResult = mutableListOf<TeamsUiState>()
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.teamState.collectLatest {
                listResult.add(it)
            }
        }
        Assert.assertTrue(listResult[0] is TeamsUiState.Loading)
        Assert.assertEquals(
            (listResult[1] as TeamsUiState.Success).teams.firstOrNull()?.getRealModelData()?.id, "767ec50c-7fdb-4c3d-98f9-d6727ef8252b"
        )
        collectJob.cancel()
    }

    @Test
    fun `get teams fail`() = runTest {
        coEvery {
            getTeamsUseCase.invoke()
        } answers {
            AppResult.Error(Throwable("No connection"))
        }
        val listResult = mutableListOf<TeamsUiState>()
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.teamState.collectLatest {
                listResult.add(it)
            }
        }
        Assert.assertTrue(listResult[0] is TeamsUiState.Loading)
        Assert.assertEquals(
            (listResult[1] as TeamsUiState.LoadFailed).error.message, "No connection"
        )
        collectJob.cancel()
    }

    @Test
    fun `get teams case empty list`() = runTest {
        coEvery {
            getTeamsUseCase.invoke()
        } answers {
            AppResult.Success(listOf())
        }
        val listResult = mutableListOf<TeamsUiState>()
        val collectJob = launch(UnconfinedTestDispatcher()) {
            viewModel.teamState.collectLatest {
                listResult.add(it)
            }
        }
        Assert.assertTrue(listResult[0] is TeamsUiState.Loading)
        Assert.assertTrue(
            (listResult[1] is TeamsUiState.EmptyList)
        )
        collectJob.cancel()
    }


}