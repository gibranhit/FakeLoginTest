package com.gibranreyes.fakelogintest.ui.login

import androidx.compose.ui.text.input.TextFieldValue
import com.gibranreyes.core.util.Outcome
import com.gibranreyes.domain.model.LoginData
import com.gibranreyes.domain.usecase.DoLoginUseCase
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)
    private lateinit var loginViewModel: LoginViewModel
    private val doLoginUseCase = mock<DoLoginUseCase>()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        loginViewModel = spy(LoginViewModel(doLoginUseCase))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `handled when login trigger an error `() = scope.runTest {
        val message = "Usuario no válido."
        val user = "joeDoe"
        val password = "test"
        whenever(doLoginUseCase.invoke(user, password)).thenReturn(
            flow { emit(Outcome.Error(message)) },
        )
        loginViewModel.userNameState.textFieldValue = TextFieldValue(user)
        loginViewModel.passwordState.textFieldValue = TextFieldValue(password)
        loginViewModel.doLogin()
        advanceUntilIdle()
        assert(loginViewModel.state.error == message)
        assert(loginViewModel.state.showDialog)
    }

    @Test
    fun `handled when login trigger a success `() = scope.runTest {
        val user = "joeDoe"
        val password = "test"
        val data = LoginData(email = "joe@test.com")
        whenever(doLoginUseCase.invoke(user, password)).thenReturn(
            flow { emit(Outcome.Success(data)) },
        )
        loginViewModel.userNameState.textFieldValue = TextFieldValue(user)
        loginViewModel.passwordState.textFieldValue = TextFieldValue(password)
        loginViewModel.doLogin()
        advanceUntilIdle()
        assert(loginViewModel.state.data == data)
        assert(loginViewModel.state.showDialog)
        assert(loginViewModel.state.data?.email == data.email)
    }

    @Test
    fun `handled when reset state login `() = scope.runTest {
        loginViewModel.resetLogin()
        advanceUntilIdle()
        assert(loginViewModel.state.data == null)
        assert(loginViewModel.state.error == null)
        assert(!loginViewModel.state.showDialog)
    }
}
