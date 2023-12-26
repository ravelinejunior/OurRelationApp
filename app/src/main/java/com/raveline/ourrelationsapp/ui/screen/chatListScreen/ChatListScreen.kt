package com.raveline.ourrelationsapp.ui.screen.chatListScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle



@Composable
fun ChatListScreen() {
    Surface {
        ChatListScreenContent()
    }
}

@Composable
fun ChatListScreenContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Chat List",
            style = TextStyle.Default
        )
    }
}
