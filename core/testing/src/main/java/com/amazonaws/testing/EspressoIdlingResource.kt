package com.amazonaws.testing

import androidx.test.espresso.IdlingResource

object EspressoIdlingResource {
    private const val resource = "GLOBAL"
    private val countingIdlingResource = SimpleCountingIdlingResource(resource).apply {

    }

    fun increment() = countingIdlingResource.increment()

    fun decrement() = countingIdlingResource.decrement()

    fun getIdlingResource(): IdlingResource = countingIdlingResource
}