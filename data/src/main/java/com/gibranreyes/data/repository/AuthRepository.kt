package com.gibranreyes.data.repository

import com.gibranreyes.core.util.Outcome
import com.gibranreyes.data.remote.response.LoginResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun doLogin(
        email: String,
        password: String,
    ): Flow<Outcome<LoginResponse>>
}
