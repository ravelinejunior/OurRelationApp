package com.raveline.ourrelationsapp.ui.navigation.routes

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.raveline.ourrelationsapp.ui.screen.splashScreen.SplashScreen

const val splashNavigationRoute = "splash_route"

fun NavGraphBuilder.splashRoute() {
    composable(splashNavigationRoute) {
        SplashScreen()
    }
}