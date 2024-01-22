package com.raveline.ourrelationsapp.ui.navigation.routes

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.raveline.ourrelationsapp.ui.domain.models.UserDataModel
import com.raveline.ourrelationsapp.ui.screen.splashScreen.SplashScreen
import com.raveline.ourrelationsapp.ui.viewmodel.OurRelationsViewModel

const val splashNavigationRoute = "splash_route"

fun NavGraphBuilder.splashRoute(
    onNavigateToHome: (UserDataModel) -> Unit,
    onNavigateToLogin: () -> Unit
) {
    composable(
        splashNavigationRoute,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Down,
                animationSpec = tween(700)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Up,
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
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                animationSpec = tween(700)
            )
        },
    ) {
        val viewModel: OurRelationsViewModel = hiltViewModel()
        SplashScreen(
            viewModel = viewModel,
            onNavigateToHome = onNavigateToHome,
            onNavigateToLogin = onNavigateToLogin,
        )
    }
}