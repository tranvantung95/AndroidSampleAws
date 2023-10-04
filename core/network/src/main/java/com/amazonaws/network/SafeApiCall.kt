package com.amazonaws.network

import com.amazonaws.model.AppResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

suspend inline fun <Model> safeApiCall(
    dispatcher: CoroutineDispatcher? = null,
    crossinline callFunction: suspend () -> Model?
): AppResult<Model>? = try {
    val response = withContext(dispatcher ?: Dispatchers.IO) {
        val result = callFunction.invoke()
        result
    }
    if (response is Response<*>) {
        if (response.errorBody() != null) {
            throw HttpException(response)
        } else {
            AppResult.Success(response)
        }
    } else {
        AppResult.Success(response)
    }
} catch (e: Exception) {
    println("API Call ${e.message}")
    withContext(Dispatchers.Main) {
        AppResult.Error(UnspecifiedErrorException(e.message))
    }
}

class UnspecifiedErrorException(message: String?) : Throwable() {
    override val message: String = message ?: "Something wrong happened"
}