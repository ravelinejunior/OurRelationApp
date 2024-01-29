package com.raveline.ourrelationsapp.ui.navigation.routes

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.raveline.ourrelationsapp.ui.domain.models.UserDataModel
import com.raveline.ourrelationsapp.ui.screen.swipeScreen.SwipeScreen

const val swipeNavigationRoute = "swipe_route"
const val userDetailsKey = "user_data"

fun NavGraphBuilder.swipeNavigationRoute(
    onNavigateToSwipe: (UserDataModel) -> Unit
) {
    composable(
        swipeNavigationRoute,
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

        SwipeScreen(
            onNavigateToSwipe = onNavigateToSwipe
        )
    }
}

fun NavController.navigateToSwipe(
    navOptions: NavOptions = navOptions { },
    userDataModel: UserDataModel?,
) {
    currentBackStackEntry?.savedStateHandle?.set(userDetailsKey, userDataModel)
    navigate(swipeNavigationRoute, navOptions)
}

