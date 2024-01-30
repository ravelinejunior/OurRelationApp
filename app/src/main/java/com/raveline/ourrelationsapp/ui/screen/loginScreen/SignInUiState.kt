package com.raveline.ourrelationsapp.ui.screen.loginScreen

import com.raveline.ourrelationsapp.ui.domain.models.UserDataModel

sealed class SignInUiState {
    data object Loading : SignInUiState()
    data object Default : SignInUiState()
    data class Failure(val message: String = String()) : SignInUiState()
    data class Success(val user: UserDataModel) : SignInUiState()
}