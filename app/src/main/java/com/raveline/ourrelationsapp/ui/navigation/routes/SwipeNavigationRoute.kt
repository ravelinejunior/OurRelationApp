package com.raveline.ourrelationsapp.ui.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.raveline.ourrelationsapp.ui.screen.swipeScreen.SwipeScreen

const val swipeNavigationRoute = "SwipeRoute"

fun NavGraphBuilder.swipeNavigationRoute() {
    composable(swipeNavigationRoute) {
        SwipeScreen()
    }
}

fun NavController.navigateToSwipe(
    navOptions: NavOptions? = null
) {
    navigate(swipeNavigationRoute, navOptions)
}