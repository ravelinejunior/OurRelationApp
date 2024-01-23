package com.raveline.ourrelationsapp.ui.screen.loginScreen

import android.annotation.SuppressLint
import android.app.Activity
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.raveline.ourrelationsapp.R
import com.raveline.ourrelationsapp.ui.common.components.NotificationMessage
import com.raveline.ourrelationsapp.ui.domain.models.UserDataModel
import com.raveline.ourrelationsapp.ui.screen.signupScreen.components.InputType
import com.raveline.ourrelationsapp.ui.screen.signupScreen.components.TextInput
import com.raveline.ourrelationsapp.ui.screen.signupScreen.components.buildExoPlayer
import com.raveline.ourrelationsapp.ui.screen.signupScreen.components.buildPlayerView
import com.raveline.ourrelationsapp.ui.theme.onPrimaryLight
import com.raveline.ourrelationsapp.ui.viewmodel.OurRelationsViewModel


@Composable
fun LoginScreen(
    activity: Activity,
    viewModel: OurRelationsViewModel,
    onNavigateToHome: (UserDataModel) -> Unit,
    onNavigateToSignUp: () -> Unit,
    content: @Composable () -> Unit
) {
    Surface {
        LoginScreenContent(
            getVideoUri(activity),
            viewModel = viewModel,
            onNavigateToHome = onNavigateToHome,
            onNavigateToSignUp = onNavigateToSignUp,
            content = content
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("OpaqueUnitKey")
@Composable
fun LoginScreenContent(
    videoUri: Uri,
    viewModel: OurRelationsViewModel,
    onNavigateToHome: (UserDataModel) -> Unit,
    onNavigateToSignUp: () -> Unit,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val passwordFocusRequester = FocusRequester()
    val emailFocusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current
    val exoPlayer = remember {
        context.buildExoPlayer(videoUri)
    }
    var userEmail by remember {
        mutableStateOf("")
    }

    var userPassword by remember {
        mutableStateOf("")
    }

    LaunchedEffect(viewModel.userState) {
           viewModel.userState.collect { user ->
            if (user != null) {
                onNavigateToHome(viewModel.userState.value!!)
            }
        }

    }

    NotificationMessage(viewModel = viewModel)

    DisposableEffect(
        AndroidView(
            factory = { it.buildPlayerView(exoPlayer) },
            modifier = Modifier.fillMaxSize()
        )
    ) {
        onDispose {
            exoPlayer.release()
        }
    }

    ProvideWindowInsets {
        val scrollableState = rememberScrollState()

        Box {
            Column(
                modifier = Modifier
                    .navigationBarsWithImePadding()
                    .padding(16.dp)
                    .fillMaxSize(),
            ) {
                Row(
                    modifier = Modifier.alpha(0.7f)
                ) {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                text = "What are you waiting for ?",
                                fontFamily = FontFamily.Cursive,
                                fontSize = 32.sp
                            )
                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = Color.Transparent,
                            actionIconContentColor = onPrimaryLight,
                            navigationIconContentColor = MaterialTheme.colorScheme.onTertiary,
                            scrolledContainerColor = MaterialTheme.colorScheme.onTertiary,
                            titleContentColor = Color.White
                        ),
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.4f),
                    verticalArrangement = Arrangement.spacedBy(
                        16.dp,
                        alignment = Alignment.CenterVertically
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.fire),
                        contentDescription = stringResource(id = R.string.app_name),
                        Modifier.size(80.dp),
                        tint = Color.White
                    )
                }

                Column(
                    modifier = Modifier.verticalScroll(scrollableState),
                    verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Bottom),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    TextInput(
                        inputType = InputType.Email,
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.clearFocus()
                            passwordFocusRequester.requestFocus()
                        }),
                        focusRequester = emailFocusRequester,
                        textValue = {
                            userEmail = it
                        }
                    )
                    TextInput(
                        inputType = InputType.Password,
                        keyboardActions = KeyboardActions(onDone = {
                            focusManager.clearFocus()
                        }),
                        focusRequester = passwordFocusRequester,
                        textValue = {
                            userPassword = it
                        }
                    )

                    Button(
                        onClick = {
                            viewModel.onSignIn(email = userEmail, password = userPassword)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            contentColor = MaterialTheme.colorScheme.onBackground
                        )
                    ) {
                        Text("LOGIN", Modifier.padding(vertical = 8.dp))
                    }

                    Divider(
                        color = Color.White.copy(alpha = 0.3f),
                        thickness = 1.dp,
                        modifier = Modifier.padding(top = 48.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Still don't have an account? Click Here",
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                        TextButton(
                            onClick = onNavigateToSignUp,
                            colors = ButtonDefaults.textButtonColors(
                                containerColor = MaterialTheme.colorScheme.background,
                                contentColor = MaterialTheme.colorScheme.onBackground
                            )
                        ) {
                            Text("SING UP")
                        }
                    }
                }

            }
            content()
        }
    }
}

@SuppressLint("DiscouragedApi")
private fun getVideoUri(
    activity: Activity
): Uri {
    val rawId =
        activity.resources.getIdentifier("clouds", "raw", activity.packageName)
    val videoUri = "android.resource://${activity.packageName}/$rawId"
    return Uri.parse(videoUri)
}
