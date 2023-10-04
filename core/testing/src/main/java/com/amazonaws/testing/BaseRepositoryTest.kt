package com.amazonaws.testing

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.amazonaws.model.AppResult
import com.amazonaws.network.APIInterface
import com.amazonaws.network.NullOnEmptyConverterFactory
import com.amazonaws.testing.rule.CoroutineRule
import com.google.gson.Gson
import io.mockk.MockKAnnotations
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

open class BaseRepositoryTest {
    companion object {
        val error = "{\n" +
                "  \"title\":\"Authentication Error\",\n" +
                "  \"description\":\"No user info found\"\n" +
                "}"
    }

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    /**
     * Rule coroutine run on main
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var mainCoroutineRule = CoroutineRule()


    private lateinit var mockWebServer: MockWebServer

    lateinit var apiService: APIInterface


    @Before
    open fun setUp() {
        MockKAnnotations.init(this)
        mockWebServer = MockWebServer()
        mockWebServer.start(8000)
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(
                OkHttpClient.Builder().apply {
                    addInterceptor(HttpLoggingInterceptor())
                }.build()
            )
            .build()
            .create(APIInterface::class.java)

    }


    /**
     * @param apiExpectResult use [HttpURLConnection] to provide api call status. eg:
     * [HttpURLConnection.HTTP_OK],
     * [HttpURLConnection.HTTP_BAD_REQUEST]
     */
    fun <T> callApi(
        apiCallFunc: suspend () -> AppResult<T>?,
        apiExpectResult: Int,
        mockResponse: String? = null,
        result: ((AppResult<T>?) -> Unit)
    ) {
        val response = MockResponse()
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .addHeader("Cache-Control", "no-cache")
            .setResponseCode(apiExpectResult)
        when (apiExpectResult) {
            HttpURLConnection.HTTP_OK -> {
                response.setBody(mockResponse.orEmpty())
            }

            else -> {
                response.setBody(error)
            }
        }
        runTest {
            try {
                mockWebServer.enqueue(response)
                result.invoke(apiCallFunc.invoke())
            } catch (e: Exception) {
                result.invoke(AppResult.Error(Throwable()))
            }
        }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
        mockWebServer.shutdown()
    }

}
