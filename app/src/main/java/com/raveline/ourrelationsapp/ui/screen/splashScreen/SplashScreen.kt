package com.raveline.ourrelationsapp.ui.screen.splashScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.RenderMode
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.raveline.ourrelationsapp.R
import com.raveline.ourrelationsapp.ui.domain.models.UserDataModel
import com.raveline.ourrelationsapp.ui.viewmodel.AuthenticationViewModel
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(
    viewModel: AuthenticationViewModel,
    onNavigateToHome: (UserDataModel) -> Unit,
    onNavigateToLogin: () -> Unit
) {

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    // Navigate to the main screen when the animation finishes
    LaunchedEffect(Unit) {
        viewModel.isUserLoggedIn()
        delay(1500L)
        if (viewModel.userState.value != null) {
            onNavigateToHome(viewModel.userState.value!!)
        } else {
            onNavigateToLogin()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background.copy(alpha = 0.7f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Our Relationship",
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 46.sp),
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Cursive,
                color = MaterialTheme.colorScheme.onBackground
            )
            LottieAnimation(
                composition = composition,
                progress = progress,
                contentScale = ContentScale.Crop,
                renderMode = RenderMode.AUTOMATIC
            )

        }
    }
}

