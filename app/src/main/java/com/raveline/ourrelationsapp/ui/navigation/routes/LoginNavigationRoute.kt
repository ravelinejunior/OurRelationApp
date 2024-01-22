package com.raveline.ourrelationsapp.ui.navigation.routes

import android.app.Activity
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.raveline.ourrelationsapp.ui.domain.models.UserDataModel
import com.raveline.ourrelationsapp.ui.screen.loginScreen.LoginScreen
import com.raveline.ourrelationsapp.ui.viewmodel.OurRelationsViewModel

const val loginNavigationRoute = "login_route"

fun NavGraphBuilder.loginNavigationRoute(
    onNavigateToHome: (UserDataModel) -> Unit,
    onNavigateToSignUp: () -> Unit,
) {
    composable(loginNavigationRoute) {
        val activity = LocalContext.current as Activity
        val viewModel: OurRelationsViewModel = hiltViewModel()
        LoginScreen(
            activity = activity,
            viewModel = viewModel,
            onNavigateToHome = onNavigateToHome,
            onNavigateToSignUp = onNavigateToSignUp
        )
    }
}

fun NavController.navigateToLogin(
    navOptions: NavOptions? = null
) {
    navigate(loginNavigationRoute, navOptions)
}