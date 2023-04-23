package com.gibranreyes.domain.mappers

import com.gibranreyes.data.remote.response.LoginResponse
import com.gibranreyes.domain.model.LoginData

fun LoginResponse.toDomainModel() = LoginData(
    email,
    firstName,
    gender,
    id,
    image,
    lastName,
    token,
    username
)
