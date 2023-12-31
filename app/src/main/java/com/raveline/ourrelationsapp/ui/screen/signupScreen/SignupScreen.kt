package com.raveline.ourrelationsapp.ui.screen.signupScreen

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.raveline.ourrelationsapp.R
import com.raveline.ourrelationsapp.ui.common.components.CommonProgressSpinner
import com.raveline.ourrelationsapp.ui.common.components.NotificationMessage
import com.raveline.ourrelationsapp.ui.screen.loginScreen.LoginScreenClass
import com.raveline.ourrelationsapp.ui.screen.signupScreen.components.InputType
import com.raveline.ourrelationsapp.ui.screen.signupScreen.components.TextInput
import com.raveline.ourrelationsapp.ui.screen.signupScreen.components.buildExoPlayer
import com.raveline.ourrelationsapp.ui.screen.signupScreen.components.buildPlayerView
import com.raveline.ourrelationsapp.ui.theme.OurRelationsAppTheme
import com.raveline.ourrelationsapp.ui.theme.onPrimaryLight
import com.raveline.ourrelationsapp.ui.viewmodel.OurRelationsViewModel
import dagger.hilt.android.AndroidEntryPoint

const val signupNavigationRoute = "SignupRoute"

@AndroidEntryPoint
class SignupScreenClass : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OurRelationsAppTheme {
                SignupScreen()
            }
        }
    }

    @Composable
    fun SignupScreen() {
        Surface {
            SignupScreenContent(getVideoUri())
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("OpaqueUnitKey")
    @Composable
    fun SignupScreenContent(videoUri: Uri) {
        val context = LocalContext.current
        val passwordFocusRequester = FocusRequester()
        val emailFocusRequester = FocusRequester()
        val focusManager = LocalFocusManager.current
        val exoPlayer = remember {
            context.buildExoPlayer(videoUri)
        }

        var userName by remember {
            mutableStateOf("")
        }

        var userEmail by remember {
            mutableStateOf("")
        }

        var userPassword by remember {
            mutableStateOf("")
        }

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
            val viewModel = hiltViewModel<OurRelationsViewModel>()
            val isLoading = viewModel.inProgress.value


            Box(
                modifier = Modifier
                    .navigationBarsWithImePadding()
                    .padding(16.dp)
                    .fillMaxSize(),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    Row {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(
                                    text = "Signup",
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
                            navigationIcon = {
                                IconButton(onClick = {
                                    navigateBack(this@SignupScreenClass, focusManager)
                                }) {
                                    Icon(
                                        imageVector = Icons.Rounded.ArrowBack,
                                        contentDescription = stringResource(
                                            id = R.string.app_name
                                        ),
                                        tint = onPrimaryLight
                                    )
                                }
                            }
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
                        verticalArrangement = Arrangement.spacedBy(
                            16.dp,
                            alignment = Alignment.Bottom
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TextInput(
                            inputType = InputType.Name,
                            keyboardActions = KeyboardActions(onNext = {
                                emailFocusRequester.requestFocus()
                            }),
                            textValue = {
                                userName = it
                            }
                        )
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
                                focusManager.clearFocus()
                                viewModel.onSignup(userName, userEmail, userPassword)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.textButtonColors(
                                containerColor = MaterialTheme.colorScheme.onTertiary,
                                contentColor = MaterialTheme.colorScheme.tertiary
                            )
                        ) {
                            Text("SIGNUP", Modifier.padding(vertical = 8.dp))
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
                                "Already have an account? Click Here",
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 4.dp)
                            )
                            TextButton(
                                onClick = {
                                    navigateBack(this@SignupScreenClass, focusManager)
                                },
                                colors = ButtonDefaults.textButtonColors(
                                    containerColor = MaterialTheme.colorScheme.onTertiary,
                                    contentColor = MaterialTheme.colorScheme.tertiary
                                )
                            ) {
                                Text("SING IN")
                            }
                        }
                    }

                }
                if (isLoading) {
                    CommonProgressSpinner()
                }

                NotificationMessage(viewModel = viewModel)
            }
        }
    }

    private fun navigateBack(
        componentActivity: ComponentActivity,
        focusManager: FocusManager
    ) {
        val intent = Intent(
            componentActivity.applicationContext,
            LoginScreenClass::class.java
        )
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        focusManager.clearFocus()
        componentActivity.startActivity(intent)
    }

    private fun getVideoUri(): Uri {
        val rawId = resources.getIdentifier("clouds", "raw", packageName)
        val videoUri = "android.resource://${packageName}/$rawId"
        return Uri.parse(videoUri)
    }

    @Preview
    @Composable
    fun PreviewSignupScreen() {
        SignupScreenContent(getVideoUri())
    }
}

