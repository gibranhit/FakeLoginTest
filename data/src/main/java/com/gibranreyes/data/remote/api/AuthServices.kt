package com.gibranreyes.data.remote.api

import com.gibranreyes.data.remote.request.LoginRequest
import com.gibranreyes.data.remote.response.LoginResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthServices {
    @POST("/auth/login")
    suspend fun login(
        @Body body: LoginRequest,
    ): ApiResponse<LoginResponse>
}
