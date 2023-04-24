package com.gibranreyes.domain.usecase

import app.cash.turbine.test
import com.gibranreyes.core.util.Outcome
import com.gibranreyes.data.remote.response.LoginResponse
import com.gibranreyes.data.repository.AuthRepository
import com.gibranreyes.domain.R
import com.gibranreyes.domain.converter.ResourceConverter
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DoLoginUseCaseTest {

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)
    private lateinit var doLoginUseCase: DoLoginUseCase
    private val authRepository = mock<AuthRepository>()
    private val resourceConverter = mock<ResourceConverter>()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        doLoginUseCase = spy(DoLoginUseCase(authRepository, resourceConverter))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `handled when username is avoided `() = scope.runTest {
        val message = "Usuario no válido."
        whenever(resourceConverter.convertString(R.string.invalid_username)).thenReturn(message)
        doLoginUseCase.invoke("", "").test {
            val emission = awaitItem()
            assert((emission as Outcome.Error).message == message)
            awaitComplete()
        }
        verify(resourceConverter).convertString(R.string.invalid_username)
    }

    @Test
    fun `handled when password is avoided `() = scope.runTest {
        val message = "Contraseña no válida."
        whenever(resourceConverter.convertString(R.string.invalid_password)).thenReturn(message)
        doLoginUseCase.invoke("joeDoe", "").test {
            val emission = awaitItem()
            assert((emission as Outcome.Error).message == message)
            awaitComplete()
        }
        verify(resourceConverter).convertString(R.string.invalid_password)
    }

    @Test
    fun `handled when user don't have internet `() = scope.runTest {
        val message = "No podemos continuar, revisa tu conexión a internet e intenta de nuevo"
        val userName = "joeDoe"
        val password = "test"
        whenever(resourceConverter.convertString(R.string.internet_error)).thenReturn(message)
        whenever(authRepository.doLogin(userName, password)).thenReturn(
            flow { emit(Outcome.Error("error internet", Outcome.Error.Type.CONNECTION)) },
        )
        doLoginUseCase.invoke(userName, password).test {
            val emission = awaitItem()
            assert((emission as Outcome.Error).message == message)
            awaitComplete()
        }
        verify(resourceConverter).convertString(R.string.internet_error)
    }

    @Test
    fun `handled when server response unknown error `() = scope.runTest {
        val message = "error"
        val userName = "joeDoe"
        val password = "test"
        whenever(authRepository.doLogin(userName, password)).thenReturn(
            flow { emit(Outcome.Error(message)) },
        )
        doLoginUseCase.invoke(userName, password).test {
            val emission = awaitItem()
            assert((emission as Outcome.Error).message == message)
            awaitComplete()
        }
    }

    @Test
    fun `handled when user has login successfully `() = scope.runTest {
        val userName = "joeDoe"
        val password = "test"

        whenever(authRepository.doLogin(userName, password))
            .thenReturn(
                flow {
                    emit(
                        Outcome.Success(LoginResponse()),
                    )
                },
            )
        doLoginUseCase.invoke(userName, password).test {
            val emission = awaitItem()
            assert((emission is Outcome.Success))
            awaitComplete()
        }
    }
}
