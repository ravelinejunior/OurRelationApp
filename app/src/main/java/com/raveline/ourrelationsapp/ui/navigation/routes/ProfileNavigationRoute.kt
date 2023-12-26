package com.raveline.ourrelationsapp.ui.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.raveline.ourrelationsapp.ui.screen.profileScreen.ProfileScreen

const val profileNavigationRoute = "ProfileRoute"

fun NavGraphBuilder.profileNavigationRoute() {
    composable(profileNavigationRoute) {
        ProfileScreen()
    }
}

fun NavController.navigateToProfile(
    navOptions: NavOptions? = null
) {
    navigate(profileNavigationRoute, navOptions)
}