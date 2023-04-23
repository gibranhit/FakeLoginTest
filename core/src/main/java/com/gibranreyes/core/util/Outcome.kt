package com.gibranreyes.core.util

sealed class Outcome<out T> {
    data class Success<out T>(val data: T) : Outcome<T>()
    data class Error(
        val message: String,
        val type: Type = Type.GENERIC,
        val resultCode: Int = 0,
    ) : Outcome<Nothing>() {
        enum class Type { GENERIC, FORMAT, CONNECTION }
    }

    val value: T? get() = if (this is Success) data else null
}
