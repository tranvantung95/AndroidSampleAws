package com.amazonaws.domain

import com.amazonaws.model.AppResult
import com.amazonaws.testing.BaseUnitTest
import com.amazonaws.testing.data.TestData
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test


class GetTeamsUseCaseTestModel : BaseUnitTest() {

    val teamsRepository: ITeamsGateway = mockk()
    lateinit var getTeamsUseCase: GetTeamsUseCase

    override fun setUp() {
        super.setUp()
        getTeamsUseCase = GetTeamsUseCase(teamsRepository)
    }

    @Test
    fun `get teams success`() = runTest {
        coEvery {
            teamsRepository.getTeams()
        } answers {
            AppResult.Success(TestData.mockTeamsData())
        }
        val result = getTeamsUseCase.invoke()
        Assert.assertTrue(result is AppResult.Success)
    }

    @Test
    fun `get teams fail`() = runTest {
        coEvery {
            teamsRepository.getTeams()
        } answers {
            AppResult.Error(Throwable("no connection"))
        }
        val result = getTeamsUseCase.invoke()
        Assert.assertTrue(result is AppResult.Error)
        Assert.assertEquals((result as AppResult.Error).error.message, "no connection")
    }

}