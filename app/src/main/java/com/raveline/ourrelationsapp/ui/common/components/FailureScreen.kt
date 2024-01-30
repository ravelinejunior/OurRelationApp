package com.raveline.ourrelationsapp.ui.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.raveline.ourrelationsapp.R

@Composable
fun FailureScreen(
    message: String,
    onRetryProduct: () -> Unit = {},
    onBackStack: () -> Unit = {}
) {

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.nothing),
    )
    val progress by animateLottieCompositionAsState(
        composition,
        isPlaying = true,
        useCompositionFrameRate = true
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            modifier = Modifier
                .size(360.dp),
            composition = composition,
            progress = { progress },
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = message,
            style = MaterialTheme.typography.titleLarge,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { onRetryProduct() },
                modifier = Modifier
                    .weight(1f)
                    .heightIn(60.dp)
                    .padding(8.dp)
            ) {
                Text(text = "Retry")
            }

            Button(
                onClick = { onBackStack() },
                modifier = Modifier
                    .weight(1f)
                    .heightIn(60.dp)
                    .padding(8.dp)
            ) {
                Text(text = "Back")
            }
        }
    }

}

@Preview
@Composable
fun FailureScreenPrev() {
    FailureScreen("Oops! Something went wrong.")
}