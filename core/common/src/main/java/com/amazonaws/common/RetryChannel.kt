package com.amazonaws.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlin.experimental.ExperimentalTypeInference

class RetryChannel private constructor(private val channel: Channel<Unit>) {

    suspend fun retry() {
        channel.send(Unit)
    }

    @OptIn(ExperimentalTypeInference::class)
    fun <R> transformLatest(@BuilderInference transform: () -> Flow<R>): Flow<R> {
        return channel
            .receiveAsFlow()
            .onStart { channel.send(Unit) }
            .flatMapLatest {
                transform()
            }
    }

    companion object {
        operator fun invoke() = RetryChannel(Channel(Channel.CONFLATED))
    }

}

fun <T> Flow<T>.fromFlowToStateFlow(runningScope: CoroutineScope, initValue: T? = null) =
    this.stateIn(
        initialValue = initValue,
        scope = runningScope,
        started = SharingStarted.WhileSubscribed(3000)
    )