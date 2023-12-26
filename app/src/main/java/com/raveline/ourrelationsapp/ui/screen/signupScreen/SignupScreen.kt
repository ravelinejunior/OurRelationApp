package com.raveline.ourrelationsapp.ui.screen.signupScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle

const val signupNavigationRoute = "SignupRoute"

@Composable
fun SignupScreen() {
    Surface {
        SignupScreenContent()
    }
}

@Composable
fun SignupScreenContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Signup",
            style = TextStyle.Default
        )
    }
}
