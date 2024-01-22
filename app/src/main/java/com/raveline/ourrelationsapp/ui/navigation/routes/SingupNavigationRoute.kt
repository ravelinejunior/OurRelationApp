package com.raveline.ourrelationsapp.ui.navigation.routes

import android.app.Activity
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.raveline.ourrelationsapp.ui.domain.models.UserDataModel
import com.raveline.ourrelationsapp.ui.screen.signupScreen.SignupScreen
import com.raveline.ourrelationsapp.ui.viewmodel.OurRelationsViewModel

const val signupNavigationRoute = "signup_route"

fun NavGraphBuilder.signupRoute(
    onNavigateToHome: (UserDataModel) -> Unit,
    onNavigateToLogin: () -> Unit
) {
    composable(signupNavigationRoute) {
        val activity = LocalContext.current as Activity
        val viewModel: OurRelationsViewModel = hiltViewModel()
        SignupScreen(
            activity = activity,
            viewModel = viewModel,
            onNavigateToHome = onNavigateToHome,
            onNavigateToLogin = onNavigateToLogin,
        )
    }
}

fun NavController.navigateToSignup(
    navOptions: NavOptions? = null
) {
    navigate(signupNavigationRoute, navOptions)
}