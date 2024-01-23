package com.raveline.ourrelationsapp.ui.navigation.navHost

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.raveline.ourrelationsapp.ui.domain.models.UserDataModel
import com.raveline.ourrelationsapp.ui.navigation.graph.homeGraph
import com.raveline.ourrelationsapp.ui.navigation.graph.homeGraphRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.navigateToLogin
import com.raveline.ourrelationsapp.ui.navigation.routes.navigateToSignup
import com.raveline.ourrelationsapp.ui.navigation.routes.navigateToSwipe
import com.raveline.ourrelationsapp.ui.navigation.routes.userDetailsKey


@Composable
fun OurRelationsNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = homeGraphRoute
    ) {
        homeGraph(
            onNavigateToLogin = {
                navController.navigateToLogin(
                    navOptions = navOptions {
                        launchSingleTop = true
                    }
                )
            },
            onNavigateToSignUp = {
                navController.navigateToSignup(
                    navOptions = navOptions {
                        launchSingleTop = true
                    }
                )
            },
            onNavigateToHome = { user ->

                navController.currentBackStackEntry?.savedStateHandle?.set(userDetailsKey, user)
                navController.navigateToSwipe(
                    navOptions {
                        launchSingleTop = true
                    },
                )
            },
            onNavigateToSignOut = {
                navController.navigateToLogin(
                    navOptions {
                        launchSingleTop = true
                        launchSingleTop
                    }
                )
            },
            onNavigateToSwipe = { _ ->
                navController.previousBackStackEntry?.savedStateHandle?.get<UserDataModel>(
                    userDetailsKey
                )
            }
        )
    }
}