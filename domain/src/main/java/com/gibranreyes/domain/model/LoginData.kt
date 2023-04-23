package com.gibranreyes.domain.model

data class LoginData(
    val email: String? = "",
    val firstName: String? = "",
    val gender: String? = "",
    val id: Int? = 0,
    val image: String? = "",
    val lastName: String? = "",
    val token: String? = "",
    val username: String? = ""
) {
    val fullName = "$firstName $lastName"
}
