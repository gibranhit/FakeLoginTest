package com.gibranreyes.data.repository

import app.cash.turbine.test
import com.gibranreyes.core.util.Outcome
import com.gibranreyes.data.MockServerUtils
import com.gibranreyes.data.MockServerUtils.enqueueResponse
import com.gibranreyes.data.remote.api.AuthServices
import com.nhaarman.mockitokotlin2.spy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalCoroutinesApi::class)
class AuthRepositoryImplTest {

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)
    private val mockWebServer = MockWebServer()
    private val authServices = MockServerUtils
        .restDataSource(
            mockWebServer,
            AuthServices::class.java,
            scope,
        )
    private lateinit var authRepository: AuthRepositoryImpl

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        authRepository = spy(AuthRepositoryImpl(authServices, dispatcher))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mockWebServer.shutdown()
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `handled when server response 200`() = scope.runTest {
        withContext(Dispatchers.IO) {
            val user = "joeDoe"
            val password = "password"
            mockWebServer.enqueueResponse("login.json")
            authRepository
            val result = authRepository.doLogin(user, password)
            result.test(Duration.INFINITE) {
                advanceUntilIdle()
                val emission = awaitItem()
                assert((emission is Outcome.Success))
                awaitComplete()
            }
            MatcherAssert.assertThat(result, CoreMatchers.`is`(CoreMatchers.notNullValue()))
        }
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `handled when server response 500`() = scope.runTest {
        withContext(Dispatchers.IO) {
            val user = "joeDoe"
            val password = "password"
            mockWebServer.enqueueResponse("error.json", 500)
            authRepository
            val result = authRepository.doLogin(user, password)
            result.test(Duration.INFINITE) {
                advanceUntilIdle()
                val emission = awaitItem()
                assert((emission is Outcome.Error))
                awaitComplete()
            }
            MatcherAssert.assertThat(result, CoreMatchers.`is`(CoreMatchers.notNullValue()))
        }
    }
}
