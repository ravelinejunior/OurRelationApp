package com.raveline.ourrelationsapp.ui.screen.profileScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.raveline.ourrelationsapp.ui.viewmodel.AuthenticationViewModel

@Composable
fun ProfileScreen(
    authenticationViewModel: AuthenticationViewModel,
    onSignOut: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.background
            )
    ) {
        ProfileScreenContent(
            authenticationViewModel = authenticationViewModel,
            onSignOut = onSignOut
        )
    }
}

@Composable
fun ProfileScreenContent(
    authenticationViewModel: AuthenticationViewModel,
    onSignOut: () -> Unit
) {

    LaunchedEffect(authenticationViewModel.userState) {
        authenticationViewModel.userState.collect { user ->
            if (user == null) {
                onSignOut()
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Profile",
            style = TextStyle.Default
        )
    }
}
