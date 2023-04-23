package com.gibranreyes.fakelogintest.ui.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue

open class TextFieldState(
    private val validator: (String) -> Boolean = { true },
    private val getErrorResForValue: (String) -> Int? = { null },
    private val clearErrorWhileFocused: Boolean = true,
    private val errorAfterLeaveFocusAndEdited: Boolean = true,
    private val errorAfterEdition: Boolean = false,
    private val errorAfterLeaveFocus: Boolean = false,
    private val errorAfterFocus: Boolean = false,
) {
    var textFieldValue by mutableStateOf(TextFieldValue())
    private var isEdited by mutableStateOf(false)
    private var hadFocus by mutableStateOf(false)
    private var showError by mutableStateOf(!(errorAfterFocus || errorAfterEdition || errorAfterLeaveFocusAndEdited || errorAfterLeaveFocus))
    val text get() = textFieldValue.text

    val valid get() = validator(textFieldValue.text)
    val error get() = if (showError && !valid) getErrorResForValue(textFieldValue.text) else null

    fun onFocusChange(hasFocus: Boolean) {
        if (hasFocus) {
            hadFocus = true
            if (clearErrorWhileFocused) disableShowErrors()
        }
        if (!hasFocus && isEdited && errorAfterLeaveFocusAndEdited && !showError) enableShowErrors()
        if (!hasFocus && hadFocus && errorAfterLeaveFocus && !showError) enableShowErrors()
        if (hasFocus && !clearErrorWhileFocused && errorAfterFocus && !showError) enableShowErrors()
    }

    fun onEdited() {
        isEdited = true
        if (errorAfterEdition && !showError) enableShowErrors()
    }

    fun enableShowErrors() {
        showError = true
    }

    fun disableShowErrors() {
        showError = false
    }
}
