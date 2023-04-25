package com.gibranreyes.fakelogintest.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.gibranreyes.fakelogintest.R
import com.gibranreyes.fakelogintest.util.TestTags.ALERT_DIALOG
import com.gibranreyes.fakelogintest.util.TestTags.BUTTON_DIALOG
import com.gibranreyes.fakelogintest.util.TestTags.ICON_DIALOG
import com.gibranreyes.fakelogintest.util.TestTags.TITLE_DIALOG

@Composable
fun SimpleAlertDialog(
    text: String,
    onClick: () -> Unit,
    isError: Boolean,
) {
    AlertDialog(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(ALERT_DIALOG),
        onDismissRequest = onClick,
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    imageVector = if (isError) Icons.Default.Warning else Icons.Default.Check,
                    contentDescription = text,
                    tint = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(50.dp)
                        .testTag(ICON_DIALOG),
                )
                Spacer(Modifier.size(15.dp))
                Text(
                    text = text,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .testTag(TITLE_DIALOG),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.size(15.dp))
            }
        },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
        ),
        confirmButton = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(BUTTON_DIALOG),
                onClick = onClick,
            ) {
                Text(
                    text = stringResource(R.string.action_ok),
                    color = Color.White,
                )
            }
        },
    )
}
