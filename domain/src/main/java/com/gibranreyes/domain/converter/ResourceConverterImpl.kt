package com.gibranreyes.domain.converter

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ResourceConverterImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : ResourceConverter {

    override fun convertString(
        @StringRes resId: Int,
        vararg params: Any,
    ): String {
        return context.resources.getString(resId, *params)
    }
}
