package com.amazonawsteams

import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import com.amazonaws.domain.GetTeamsUseCase
import com.amazonaws.model.AppResult
import com.amazonaws.testing.data.TestData
import com.amazonawsteams.screen.TeamsFragmentScreen
import com.amazonawsteams.teamList.TeamsFragment
import com.amazonawsteams.teamList.TeamsViewModel
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import com.amazonaws.common.R as RCommon

class TeamsModelFragmentTest : TestCase() {
    @Rule
    @JvmField
    var mInstantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val countingTaskExecutorRule = CountingTaskExecutorRule()

    @ExperimentalCoroutinesApi
    val testDispatcher = TestCoroutineDispatcher()

    var fragmentScenario: FragmentScenario<TeamsFragment>? = null

    var mockModule: Module = module(createdAtStart = true, override = true) {
        viewModel {
            viewModel
        }
    }
    private val teamsUseCase: GetTeamsUseCase = mockk()

    val viewModel: TeamsViewModel = spyk(TeamsViewModel(teamsUseCase))

    @Before
    fun setUp() {
        loadKoinModules(mockModule)
    }

    @Test
    fun testCaseHaveData() {
        coEvery {
            teamsUseCase.invoke()
        } answers {
            AppResult.Success(TestData.mockTeamsData())
        }
        fragmentScenario = launchFragmentInContainer(themeResId = RCommon.style.Theme_Amazonaws)
        run {
            step("init Screen") {
                TeamsFragmentScreen {
                    rcTeams {
                        isVisible()
                    }
                }
            }
            step("check size") {
                TeamsFragmentScreen {
                    rcTeams {
                        hasSize(10)
                    }
                }
            }
        }
    }

    @Test
    fun testCaseDataIsEmpty() {
        coEvery {
            teamsUseCase.invoke()
        } answers {
            AppResult.Success(listOf())
        }
        fragmentScenario = launchFragmentInContainer(themeResId = RCommon.style.Theme_Amazonaws)
        run {
            step("init Screen") {
                TeamsFragmentScreen {
                    tvEmpty {
                        isVisible()
                    }
                }
            }
        }
    }

    @After
    fun clear() {
        unmockkAll()
    }

}