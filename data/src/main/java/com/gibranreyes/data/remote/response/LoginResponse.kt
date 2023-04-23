package com.gibranreyes.data.remote.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginResponse(
    @Json(name = "email")
    val email: String? = "",
    @Json(name = "firstName")
    val firstName: String? = "",
    @Json(name = "gender")
    val gender: String? = "",
    @Json(name = "id")
    val id: Int? = 0,
    @Json(name = "image")
    val image: String? = "",
    @Json(name = "lastName")
    val lastName: String? = "",
    @Json(name = "token")
    val token: String? = "",
    @Json(name = "username")
    val username: String? = ""
)
