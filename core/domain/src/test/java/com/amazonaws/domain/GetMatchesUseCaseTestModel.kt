package com.amazonaws.domain

import com.amazonaws.model.AppResult
import com.amazonaws.testing.BaseUnitTest
import com.amazonaws.testing.data.TestData
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class GetMatchesUseCaseTestModel : BaseUnitTest() {
    val matchesRepo: IMatchesGateway = mockk()

    lateinit var getTeamsUseCase: GetMatchesUseCase
    override fun setUp() {
        super.setUp()
        getTeamsUseCase = GetMatchesUseCase(matchesRepo)
    }

    @Test
    fun `test case get matches success`() = runTest {
        coEvery {
            matchesRepo.getMatches()
        } answers {
            AppResult.Success(TestData.mockMatchesData())
        }
        val result = getTeamsUseCase.invoke()
        Assert.assertTrue(result is AppResult.Success)
    }

    @Test
    fun `test case get matches fail`() = runTest {
        coEvery {
            matchesRepo.getMatches()
        } answers {
            AppResult.Error(Throwable("no connection"))
        }
        val result = getTeamsUseCase.invoke()
        Assert.assertTrue(result is AppResult.Error)
        Assert.assertEquals((result as AppResult.Error).error.message, "no connection")
    }
}