package com.raveline.ourrelationsapp.ui.navigation.navHost

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import com.raveline.ourrelationsapp.ui.domain.models.UserDataModel
import com.raveline.ourrelationsapp.ui.navigation.routes.chatListNavigationRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.chatListRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.loginNavigationRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.profileIntroNavigationRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.profileNavigationRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.signupNavigationRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.signupRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.splashNavigationRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.splashRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.swipeNavigationRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.userDetailsKey

@Composable
fun OurRelationsNavigationHost(
    navController: NavHostController,
    bottomPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = splashNavigationRoute,
        modifier = Modifier.padding(bottom = bottomPadding.calculateBottomPadding())
    ) {

        splashRoute(
            onNavigateToHome = {},
            onNavigateToLogin = {}
        )

        loginNavigationRoute(
            onNavigateToHome = {},
            onNavigateToSignUp = {}
        )

        signupRoute(
            onNavigateToHome = {},
            onNavigateToLogin = {}
        )

        swipeNavigationRoute(
            onNavigateToSwipe = {}
        )

        profileNavigationRoute(
            onSignOut = {},
            navigateToEditProfile = {}
        )

        chatListRoute()

    }
}


private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
}

fun NavController.navigateToSwipe(
    navOptions: NavOptions = androidx.navigation.navOptions { }
) {
    navigate(swipeNavigationRoute, navOptions)
}


private fun NavController.navigateToLogin(
    navOptions: NavOptions? = null
) {
    navigate(loginNavigationRoute, navOptions)
}

fun NavController.navigateToSignup(
    navOptions: NavOptions? = null
) {
    navigate(signupNavigationRoute, navOptions)
}

fun NavController.navigateToIntroProfile(
    navOptions: NavOptions? = null,
) {
    navigate(profileIntroNavigationRoute, navOptions)
}

fun NavController.navigateToProfile(
    navOptions: NavOptions? = null,
    userData: UserDataModel?
) {
    navigate("$profileNavigationRoute/{$userDetailsKey}={$userData}", navOptions)
}

private fun NavController.navigateToChatList(
    navOptions: NavOptions? = null
) {
    navigate(chatListNavigationRoute, navOptions)
}