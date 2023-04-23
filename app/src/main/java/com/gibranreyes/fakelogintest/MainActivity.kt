package com.gibranreyes.fakelogintest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.gibranreyes.fakelogintest.ui.login.LoginScreen
import com.gibranreyes.fakelogintest.ui.theme.FakeLoginTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            FakeLoginTestTheme {
                LoginScreen()
            }
        }
    }
}
