package com.raveline.ourrelationsapp.ui.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.raveline.ourrelationsapp.ui.domain.models.UserDataModel
import com.raveline.ourrelationsapp.ui.screen.swipeScreen.SwipeScreen

const val swipeNavigationRoute = "SwipeRoute"
const val userDetailsKey = "user_data"

fun NavGraphBuilder.swipeNavigationRoute(
    onNavigateToSwipe: (UserDataModel) -> Unit
) {
    composable(swipeNavigationRoute) {

        SwipeScreen(
            onNavigateToSwipe = onNavigateToSwipe
        )
    }
}

fun NavController.navigateToSwipe(
    navOptions: NavOptions? = null
) {
    navigate(swipeNavigationRoute, navOptions)
}