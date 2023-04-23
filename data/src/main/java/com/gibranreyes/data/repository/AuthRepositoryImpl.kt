package com.gibranreyes.data.repository

import com.gibranreyes.core.util.Outcome
import com.gibranreyes.data.remote.api.AuthServices
import com.gibranreyes.data.remote.request.LoginRequest
import com.gibranreyes.data.remote.response.ErrorResponse
import com.gibranreyes.data.remote.response.LoginResponse
import com.skydoves.sandwich.message
import com.skydoves.sandwich.serialization.deserializeErrorBody
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.net.UnknownHostException
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
                val error = this.deserializeErrorBody<LoginResponse, ErrorResponse>()
                emit(Outcome.Error(error?.message.orEmpty()))
            }.suspendOnException {
                val typeErrorResponse = if (this.exception is UnknownHostException) {
                    Outcome.Error.Type.CONNECTION
                } else {
                    Outcome.Error.Type.GENERIC
                }
                emit(Outcome.Error(this.message(), typeErrorResponse))
            }
        }.flowOn(dispatcher)
    }
}
