package com.gibranreyes.data.repository

import com.gibranreyes.core.util.Outcome
import com.gibranreyes.data.remote.api.AuthServices
import com.gibranreyes.data.remote.request.LoginRequest
import com.gibranreyes.data.remote.response.ErrorEnvelopeMapper
import com.gibranreyes.data.remote.response.LoginResponse
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnFailure
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authServices: AuthServices,
    private val dispatcher: CoroutineDispatcher,
) : AuthRepository {
    override suspend fun doLogin(
        email: String,
        password: String,
    ): Flow<Outcome<LoginResponse>> {
        return flow {
            val response = authServices.login(LoginRequest(email, password))
            response.suspendOnSuccess {
                emit(Outcome.Success(this.data))
            }.suspendOnError {
                emit(Outcome.Error(this.message()))
            }.suspendOnFailure {
                    this.message()
            }.suspendOnException {
                emit(Outcome.Error(this.message()))
            }
        }.flowOn(dispatcher)
    }
}
