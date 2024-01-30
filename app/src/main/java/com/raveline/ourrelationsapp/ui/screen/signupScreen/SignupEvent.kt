package com.raveline.ourrelationsapp.ui.screen.signupScreen

sealed class SignupEvent {
    data class SignupUser(
        val userName: String,
        val email: String,
        val password: String,
    ) : SignupEvent()

    data object GetFirebaseUserData : SignupEvent()
}