package com.gibranreyes.domain.converter

import androidx.annotation.StringRes

interface ResourceConverter {
    fun convertString(@StringRes resId: Int, vararg params: Any): String
}
