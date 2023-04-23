package com.gibranreyes.fakelogintest.ui.login

import com.gibranreyes.domain.model.LoginData

data class LoginState(
    val data: LoginData? = null,
    val error: String? = null,
    val showDialog: Boolean = false,
    val isLoading: Boolean = false,
)
