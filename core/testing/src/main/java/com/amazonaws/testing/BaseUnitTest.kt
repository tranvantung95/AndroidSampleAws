package com.amazonaws.testing

import com.amazonaws.testing.rule.CoroutineRule
import com.amazonaws.testing.rule.InstantTaskExecutorRule
import io.mockk.MockKAnnotations
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule

open class BaseUnitTest {
    /**
     * This rule make Architecture Component (as LiveData) working with JUnit
     */
    @Rule
    @JvmField
    val instantTaskExecutorRule: TestRule =
        InstantTaskExecutorRule()

    /**
     * Rule coroutine run on main
     */
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = CoroutineRule()


    @Before
    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    open fun setUp() {
        // make annotation work
        MockKAnnotations.init(this)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        unmockkAll()
        //mainCoroutineRule.cancel()
    }

}