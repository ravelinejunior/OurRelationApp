package com.raveline.ourrelationsapp.ui.common.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.raveline.ourrelationsapp.ui.screen.loginScreen.SignInViewModel
import com.raveline.ourrelationsapp.ui.screen.signupScreen.SignupState
import com.raveline.ourrelationsapp.ui.screen.signupScreen.SignupViewModel
import com.raveline.ourrelationsapp.ui.viewmodel.AuthenticationViewModel

@Composable
fun CommonProgressSpinner(
    state: SignupState
) {
    if (state.inProgress.value) {
        Box(
            modifier = Modifier
                .alpha(0.5f)
                .background(Color.LightGray)
                .clickable(enabled = false) {}
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(100.dp),
                color = Color.Black,
                strokeWidth = 6.dp
            )
        }
    }
}

@Composable
fun CommonProgressSpinner() {
    Box(
        modifier = Modifier
            .alpha(0.5f)
            .background(Color.LightGray)
            .clickable(enabled = false) {}
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(100.dp),
            color = Color.Black,
            strokeWidth = 6.dp
        )
    }
}

@Composable
fun CommonProgress(
    viewModel: ViewModel,
) {
    when (viewModel) {
        is SignupViewModel -> {
            if (viewModel.inProgress.value) {
                Box(
                    modifier = Modifier
                        .alpha(0.5f)
                        .background(Color.LightGray)
                        .clickable(enabled = false) {}
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(100.dp),
                        color = Color.Black,
                        strokeWidth = 6.dp
                    )
                }
            }
        }

        is SignInViewModel -> {
            if (viewModel.inProgress.value) {
                Box(
                    modifier = Modifier
                        .alpha(0.5f)
                        .background(Color.LightGray)
                        .clickable(enabled = false) {}
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(100.dp),
                        color = Color.Black,
                        strokeWidth = 6.dp
                    )
                }
            }
        }

        is AuthenticationViewModel -> {
            if (viewModel.inProgress.value) {
                Box(
                    modifier = Modifier
                        .alpha(0.5f)
                        .background(Color.LightGray)
                        .clickable(enabled = false) {}
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(100.dp),
                        color = Color.Black,
                        strokeWidth = 6.dp
                    )
                }
            }
        }
    }

}

@Composable
fun NotificationMessageLogin(viewModel: SignInViewModel) {
    val notificationState = viewModel.popUpNotification.value
    val notifyMessage = notificationState?.getContentOrNull()
    if (!notifyMessage.isNullOrEmpty()) {
        Toast.makeText(LocalContext.current, notifyMessage, Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun NotificationMessageSignup(viewModel: SignupViewModel) {
    val notificationState = viewModel.popUpNotification.value
    val notifyMessage = notificationState?.getContentOrNull()
    if (!notifyMessage.isNullOrEmpty()) {
        Toast.makeText(LocalContext.current, notifyMessage, Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun NotificationMessage(viewModel: AuthenticationViewModel) {
    val notificationState = viewModel.popUpNotification.value
    val notifyMessage = notificationState?.getContentOrNull()
    if (!notifyMessage.isNullOrEmpty()) {
        Toast.makeText(LocalContext.current, notifyMessage, Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun CommonDivider() {
    Divider(
        color = Color.LightGray,
        thickness = 1.dp,
        modifier = Modifier
            .alpha(0.3f)
            .padding()
    )
}

@Composable
fun CommonSpacer(value: Dp = 16.dp) {
    Spacer(modifier = Modifier.height(value))
}
