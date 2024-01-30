package com.raveline.ourrelationsapp.ui.screen.signupScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.raveline.ourrelationsapp.ui.domain.models.UserDataModel
import kotlinx.coroutines.flow.MutableStateFlow

data class SignupState(
    val inProgress: MutableState<Boolean> = mutableStateOf(false),
    val userModel: MutableStateFlow<UserDataModel?>? = null

)