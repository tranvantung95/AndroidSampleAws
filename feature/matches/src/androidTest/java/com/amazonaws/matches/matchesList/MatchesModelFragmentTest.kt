package com.amazonaws.matches.matchesList

import androidx.arch.core.executor.testing.CountingTaskExecutorRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import com.amazonaws.domain.GetMatchesUseCase
import com.amazonaws.matches.screen.MatchesScreen
import com.amazonaws.model.AppResult
import com.amazonaws.testing.data.TestData
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

class MatchesModelFragmentTest : TestCase() {
    @Rule
    @JvmField
    var mInstantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val countingTaskExecutorRule = CountingTaskExecutorRule()

    private val getMatchesUseCase: GetMatchesUseCase = mockk()

    @ExperimentalCoroutinesApi
    val testDispatcher = TestCoroutineDispatcher()

    var fragmentScenario: FragmentScenario<MatchesFragment>? = null

    private var mockModule: Module = module(createdAtStart = true, override = true) {
        viewModel {
            viewModel
        }
    }
    private val viewModel: MatchesViewModel = spyk(MatchesViewModel(getMatchesUseCase))

    @Before
    fun setUp() {
        loadKoinModules(mockModule)
    }

    @Test
    fun testScreenPreviousMatches() {
        coEvery {
            getMatchesUseCase.invoke()
        } answers {
            AppResult.Success(TestData.mockMatchesData())
        }
        fragmentScenario = launchFragmentInContainer(
            themeResId = RCommon.style.Theme_Amazonaws,
            fragmentArgs = bundleOf()
        )
        run {
            step("wait view show") {
                MatchesScreen {
                    previousRecycler {
                        isVisible()
                    }
                    previousRecycler {
                        firstChild<MatchesScreen.MatchesItem> {
                            btSchedule {
                                isGone()
                            }
                            btHighlights {
                                isVisible()
                            }
                        }
                    }
                }
            }
            step("change to up coming") {
                MatchesScreen {
                    tabMatches {
                        selectTab(1)
                    }
                }
            }
            step("tab up coming is show") {
                MatchesScreen {
                    upComingRecycler {
                        isVisible()
                    }
                    upComingRecycler {
                        firstChild<MatchesScreen.MatchesItem> {
                            btSchedule {
                                isVisible()
                            }
                            btHighlights {
                                isGone()
                            }
                        }
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