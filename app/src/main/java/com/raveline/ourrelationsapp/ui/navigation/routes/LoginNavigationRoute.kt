package com.raveline.ourrelationsapp.ui.navigation.routes

import android.app.Activity
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.raveline.ourrelationsapp.ui.common.components.CommonProgress
import com.raveline.ourrelationsapp.ui.common.components.CommonProgressSpinner
import com.raveline.ourrelationsapp.ui.domain.models.UserDataModel
import com.raveline.ourrelationsapp.ui.screen.loginScreen.LoginScreen
import com.raveline.ourrelationsapp.ui.viewmodel.OurRelationsViewModel

const val loginNavigationRoute = "login_route"

fun NavGraphBuilder.loginNavigationRoute(
    onNavigateToHome: (UserDataModel) -> Unit,
    onNavigateToSignUp: () -> Unit,
) {
    composable(
        loginNavigationRoute,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                animationSpec = tween(700)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                animationSpec = tween(700)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                animationSpec = tween(700)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.End,
                animationSpec = tween(700)
            )
        },
    ) {
        val activity = LocalContext.current as Activity
        val viewModel: OurRelationsViewModel = hiltViewModel()
        LoginScreen(
            activity = activity,
            viewModel = viewModel,
            onNavigateToHome = onNavigateToHome,
            onNavigateToSignUp = onNavigateToSignUp,
            content = {
                CommonProgress(viewModel)
            }
        )
    }
}

fun NavController.navigateToLogin(
    navOptions: NavOptions? = null
) {
    navigate(loginNavigationRoute, navOptions)
}