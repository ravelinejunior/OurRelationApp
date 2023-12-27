package com.raveline.ourrelationsapp.ui.screen.singleChatScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun SingleChatScreen(chatId:String) {
    Surface {
        SingleChatScreenContent(chatId)
    }
}

@Composable
fun SingleChatScreenContent(chatId:String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Single Chat Screen",
            style = TextStyle.Default
        )
    }
}

@Preview
@Composable
fun SingleChatScreenPreview() {
    SingleChatScreen("")
}