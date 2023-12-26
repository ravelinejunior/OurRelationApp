package com.raveline.ourrelationsapp.ui.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.raveline.ourrelationsapp.ui.screen.loginScreen.LoginScreen

const val loginNavigationRoute = "LoginRoute"

fun NavGraphBuilder.loginNavigationRoute() {
    composable(loginNavigationRoute) {
        LoginScreen()
    }
}

fun NavController.navigateToLogin(
    navOptions: NavOptions? = null
) {
    navigate(loginNavigationRoute, navOptions)
}