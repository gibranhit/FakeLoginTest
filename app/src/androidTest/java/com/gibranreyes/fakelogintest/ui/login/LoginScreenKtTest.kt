package com.gibranreyes.fakelogintest.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.gibranreyes.domain.model.LoginData
import com.gibranreyes.fakelogintest.ui.components.TextFieldState
import com.gibranreyes.fakelogintest.util.TestTags.ALERT_DIALOG
import com.gibranreyes.fakelogintest.util.TestTags.LOADER
import com.gibranreyes.fakelogintest.util.TestTags.LOGIN_BUTTON
import com.gibranreyes.fakelogintest.util.TestTags.PASSWORD_ICON_BUTTON
import com.gibranreyes.fakelogintest.util.TestTags.PASSWORD_NAME_INPUT
import com.gibranreyes.fakelogintest.util.TestTags.USER_NAME_INPUT
import org.junit.Rule
import org.junit.Test

class LoginScreenKtTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun doLogin_CheckDialogAndDismiss() {
        var state by mutableStateOf(LoginState())
        val userName = "JoeDoe"
        composeRule.setContent {
            LoginScreenContent(
                state = state,
                emailTextFieldState = TextFieldState(),
                passwordTextFieldState = TextFieldState(),
                onLogin = {
                    state = state.copy(
                        showDialog = true,
                        data = LoginData(username = userName),
                    )
                },
                resetLogin = {},
            )
        }
        assert(!state.showDialog)
        assert(state.data == null)
        composeRule.onNodeWithTag(ALERT_DIALOG).assertDoesNotExist()
        composeRule.onNodeWithTag(LOGIN_BUTTON).performClick()
        assert(state.showDialog)
        assert(state.data != null)
        composeRule.onNodeWithTag(ALERT_DIALOG).assertIsDisplayed()
    }

    @Test
    fun doLogin_ClickLoggingButtonShowLoaderAndDismiss() {
        var state by mutableStateOf(LoginState())
        composeRule.setContent {
            LoginScreenContent(
                state = state,
                emailTextFieldState = TextFieldState(),
                passwordTextFieldState = TextFieldState(),
                onLogin = {
                    state = state.copy(isLoading = true)
                },
                resetLogin = {},
            )
        }
        assert(!state.isLoading)
        composeRule.onNodeWithTag(LOADER).assertDoesNotExist()
        composeRule.onNodeWithTag(LOGIN_BUTTON).performClick()
        assert(state.isLoading)
        composeRule.onNodeWithTag(LOADER).assertIsDisplayed()
    }

    @Test
    fun doLogin_setInputToTextFields() {
        val userName = "JoeDoe"
        val password = "test"
        composeRule.setContent {
            LoginScreenContent(
                state = LoginState(),
                emailTextFieldState = TextFieldState(),
                passwordTextFieldState = TextFieldState(),
                onLogin = {},
                resetLogin = {},
            )
        }
        composeRule.onNodeWithTag(PASSWORD_ICON_BUTTON).performClick()
        composeRule.onNodeWithTag(USER_NAME_INPUT).performTextInput(userName)
        composeRule.onNodeWithText(userName).assertIsDisplayed()
        composeRule.onNodeWithTag(USER_NAME_INPUT).assert(hasText(userName))
        composeRule.onNodeWithTag(PASSWORD_NAME_INPUT).performTextInput(password)
        composeRule.onNodeWithTag(PASSWORD_NAME_INPUT).assert(hasText(password))
    }
}
