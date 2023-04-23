package com.gibranreyes.data.remote.response

import com.skydoves.sandwich.ApiErrorModelMapper
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.serialization.deserializeErrorBody
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val message: String,
)

object ErrorEnvelopeMapper : ApiErrorModelMapper<ErrorResponse> {
    override fun map(apiErrorResponse: ApiResponse.Failure.Error<*>): ErrorResponse {
        return apiErrorResponse.deserializeErrorBody()?:ErrorResponse("error")
    }
}
