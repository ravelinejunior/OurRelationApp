package com.raveline.ourrelationsapp.ui.navigation.routes

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.raveline.ourrelationsapp.ui.screen.profileScreen.ProfileScreen
import com.raveline.ourrelationsapp.ui.viewmodel.AuthenticationViewModel

const val profileNavigationRoute = "profile_route"

fun NavGraphBuilder.profileNavigationRoute(
    onSignOut: () -> Unit
) {
    composable(profileNavigationRoute) {
        val viewModel: AuthenticationViewModel = hiltViewModel()
        ProfileScreen(
            authenticationViewModel = viewModel,
            onSignOut = onSignOut
        )
    }
}

fun NavController.navigateToProfile(
    navOptions: NavOptions? = null
) {
    navigate(profileNavigationRoute, navOptions)
}