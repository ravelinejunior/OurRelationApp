package com.raveline.ourrelationsapp.ui.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.raveline.ourrelationsapp.ui.screen.signupScreen.SignupScreen

const val signupNavigationRoute = "SignupRoute"

fun NavGraphBuilder.signupRoute() {
    composable(signupNavigationRoute) {
        SignupScreen()
    }
}

fun NavController.navigateToSignup(
    navOptions: NavOptions? = null
) {
    navigate(signupNavigationRoute, navOptions)
}