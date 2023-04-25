package com.gibranreyes.fakelogintest.ui.components

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertContentDescriptionContains
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.gibranreyes.fakelogintest.util.TestTags
import org.junit.Rule
import org.junit.Test

class AlertDialogUiTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun dialog_DataInDialogIsSuccess() {
        val message = "error"
        var isClickDialog = false
        composeRule.setContent {
            SimpleAlertDialog(
                message,
                onClick = {
                    isClickDialog = true
                },
                isError = false,
            )
        }
        assert(!isClickDialog)
        composeRule.onNodeWithTag(TestTags.TITLE_DIALOG).assert(hasText(message))
        composeRule.onNodeWithTag(TestTags.BUTTON_DIALOG).performClick()
        composeRule.onNodeWithTag(TestTags.ICON_DIALOG).assertContentDescriptionContains(message)
        assert(isClickDialog)
    }

    @Test
    fun dialog_DataInDialogIsError() {
        val message = "JoeDoe"
        var isClickDialog = false
        composeRule.setContent {
            SimpleAlertDialog(
                message,
                onClick = {
                    isClickDialog = true
                },
                isError = true,
            )
        }
        assert(!isClickDialog)
        composeRule.onNodeWithTag(TestTags.TITLE_DIALOG).assert(hasText(message))
        composeRule.onNodeWithTag(TestTags.BUTTON_DIALOG).performClick()
        composeRule.onNodeWithTag(TestTags.ICON_DIALOG).assertContentDescriptionContains(message)
        assert(isClickDialog)
    }
}
