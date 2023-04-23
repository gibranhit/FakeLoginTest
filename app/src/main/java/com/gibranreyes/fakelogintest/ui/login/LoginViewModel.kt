package com.gibranreyes.fakelogintest.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gibranreyes.core.util.Outcome
import com.gibranreyes.domain.usecase.DoLoginUseCase
import com.gibranreyes.fakelogintest.R
import com.gibranreyes.fakelogintest.ext.isEmail
import com.gibranreyes.fakelogintest.ui.components.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val doLoginUseCase: DoLoginUseCase,
) : ViewModel() {

    val userNameState =
        TextFieldState({ it.isNotBlank() }, { R.string.invalid_username })
    val passwordState =
        TextFieldState({ it.isNotBlank() }, { R.string.invalid_password })
    var state by mutableStateOf(LoginState())
        private set

    fun doLogin() {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            doLoginUseCase.invoke(
                userNameState.text,
                passwordState.text,
            ).collect {
                state = when (it) {
                    is Outcome.Success -> {
                        state.copy(
                            data = it.data,
                            showDialog = true,
                            isLoading = false,
                        )
                    }

                    is Outcome.Error -> {
                        state.copy(
                            error = it.message,
                            showDialog = true,
                            isLoading = false,
                        )
                    }
                }
            }
        }
    }

    fun resetLogin() {
        state = state.copy(data = null, error = null, showDialog = false)
    }
}
