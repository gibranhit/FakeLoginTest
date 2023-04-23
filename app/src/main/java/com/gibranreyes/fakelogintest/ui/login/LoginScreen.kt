package com.gibranreyes.fakelogintest.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gibranreyes.fakelogintest.R
import com.gibranreyes.fakelogintest.ui.components.OutlinedTextFieldCustom
import com.gibranreyes.fakelogintest.ui.components.SimpleAlertDialog
import com.gibranreyes.fakelogintest.ui.components.TextFieldState
import com.gibranreyes.fakelogintest.ui.theme.FakeLoginTestTheme

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel()) {
    LoginScreenContent(
        viewModel.state,
        viewModel.emailState,
        viewModel.passwordState,
        onLogin = {
            viewModel.doLogin()
        },
        resetLogin = {
            viewModel.resetLogin()
        },
    )
}

@Composable
fun LoginScreenContent(
    state: LoginState,
    emailTextFieldState: TextFieldState,
    passwordTextFieldState: TextFieldState,
    onLogin: () -> Unit,
    resetLogin: () -> Unit,
) {
    // Code to Show and Dismiss Dialog

    val passwordFocusRequester = remember { FocusRequester() }
    val emailFocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.app_icon),
                contentDescription = null,
                modifier = Modifier.weight(1f).fillMaxWidth(),
                contentScale = ContentScale.Inside,
            )
            Surface(
                shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
                contentColor = MaterialTheme.colorScheme.background,
                modifier = Modifier.weight(2f).fillMaxWidth(),
            ) {
                Column(modifier = Modifier.fillMaxSize().padding(10.dp)) {
                    Spacer(Modifier.size(15.dp))
                    Text(
                        stringResource(R.string.action_sign_in_email),
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(Modifier.size(15.dp))
                    OutlinedTextFieldCustom(
                        value = emailTextFieldState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(emailFocusRequester),
                        label = {
                            Text(text = stringResource(R.string.prompt_email))
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next,
                        ),
                        keyboardActions = KeyboardActions { passwordFocusRequester.requestFocus() },
                    )
                    Spacer(Modifier.size(15.dp))
                    val passwordVisibility = remember { mutableStateOf(true) }
                    OutlinedTextFieldCustom(
                        value = passwordTextFieldState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(passwordFocusRequester),
                        label = {
                            Text(text = stringResource(R.string.prompt_password))
                        },
                        trailingIcon = {
                            IconButton(onClick = {
                                passwordVisibility.value = !passwordVisibility.value
                            }) {
                                Icon(
                                    imageVector = if (passwordVisibility.value) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = "visibility",
                                )
                            }
                        },
                        visualTransformation = if (passwordVisibility.value) PasswordVisualTransformation() else VisualTransformation.None,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next,
                        ),
                        keyboardActions = KeyboardActions { focusManager.clearFocus() },
                    )
                    Spacer(Modifier.size(15.dp))
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onLogin,
                    ) {
                        Text(
                            text = stringResource(R.string.prompt_password),
                            color = Color.White,
                        )
                    }
                }
            }
        }

        if (state.showDialog) {
            SimpleAlertDialog(
                text = state.error ?: state.data?.lastName.orEmpty(),
                onClick = resetLogin,
            )
        }
    }
}

@Preview
@Composable
fun PreviewLoginScreen() {
    FakeLoginTestTheme(false) {
        LoginScreenContent(
            LoginState(showDialog = true),
            TextFieldState(),
            TextFieldState(),
            onLogin = {},
            resetLogin = {},
        )
    }
}