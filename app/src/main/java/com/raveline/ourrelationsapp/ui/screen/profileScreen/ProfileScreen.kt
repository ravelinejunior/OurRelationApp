package com.raveline.ourrelationsapp.ui.screen.profileScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle


@Composable
fun ProfileScreen() {
    Surface {
        ProfileScreenContent()
    }
}

@Composable
fun ProfileScreenContent() {
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
