package com.raveline.ourrelationsapp.ui.navigation.navHost

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
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

import com.raveline.ourrelationsapp.ui.screen.profileScreen.ProfileScreen
import com.raveline.ourrelationsapp.ui.viewmodel.AuthenticationViewModel

@Composable
fun OurRelationsNavigationHost(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = splashNavigationRoute,
    ) {

        splashRoute(
            onNavigateToHome = {
                navController.navigateToSwipe(
                    userData = it
                )
            },
            onNavigateToLogin = {
                navController.navigateToLogin()
            }
        )

        loginNavigationRoute(
            onNavigateToHome = {
                navController.navigateToSwipe(
                    userData = it
                )
            },
            onNavigateToSignUp = {
                navController.navigateToSignup()
            }
        )

        signupRoute(
            onNavigateToHome = {
                navController.navigateToSwipe(
                    userData = it
                )
            },
            onNavigateToLogin = {
                navController.navigateToLogin()
            }
        )

        swipeNavigationRoute(
            onNavigateToSwipe = {
                navController.navigateToSwipe(
                    userData = it
                )
            },
        )

        profileNavigationRoute(
            onSignOut = {
                navController.navigateToLogin(
                    navOptions {
                        launchSingleTop = true
                    }
                )
            },
            navigateToEditProfile = {
                navController.navigateToProfile(
                    userData = it
                )
            },
            navController = navController
        )

        profileEditRoute(navController)

        chatListRoute()

    }
}

fun NavGraphBuilder.profileEditRoute(navController: NavController) {
    composable(
        profileNavigationRoute,
    ) {
        navController.previousBackStackEntry?.savedStateHandle?.get<UserDataModel>(
            userDetailsKey
        )?.let {
            val viewModel: AuthenticationViewModel = hiltViewModel()
            ProfileScreen(
                vm = viewModel,
                userData = it,
            )
        }
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
    navOptions: NavOptions = navOptions {
        launchSingleTop = true
        restoreState = true
    },
    userData: UserDataModel?
) {
    currentBackStackEntry?.savedStateHandle?.set(userDetailsKey, userData)
    navigate(swipeNavigationRoute, navOptions)
}


fun NavController.navigateToLogin(
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
    userData: UserDataModel?
) {
    currentBackStackEntry?.savedStateHandle?.set(userDetailsKey, userData)
    navigate(profileIntroNavigationRoute, navOptions)
}

fun NavController.navigateToProfile(
    navOptions: NavOptions? = null,
    userData: UserDataModel?
) {
    currentBackStackEntry?.savedStateHandle?.set(userDetailsKey, userData)
    navigate(profileNavigationRoute, navOptions)
}

private fun NavController.navigateToChatList(
    navOptions: NavOptions? = null
) {
    navigate(chatListNavigationRoute, navOptions)
}