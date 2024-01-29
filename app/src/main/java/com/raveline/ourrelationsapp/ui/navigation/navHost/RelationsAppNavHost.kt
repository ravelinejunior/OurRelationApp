package com.raveline.ourrelationsapp.ui.navigation.navHost

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.raveline.ourrelationsapp.ui.navigation.graph.homeGraph
import com.raveline.ourrelationsapp.ui.navigation.graph.homeGraphRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.navigateToIntroProfile
import com.raveline.ourrelationsapp.ui.navigation.routes.navigateToLogin
import com.raveline.ourrelationsapp.ui.navigation.routes.navigateToProfile
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
            navController = navController,
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
                navController.navigateToSwipe(
                    userData = user
                )
            },
            onNavigateToSignOut = {
                navController.navigateToLogin(
                    navOptions {
                        launchSingleTop = true
                    }
                )
            },
            onNavigateToSwipe = { user  ->
                navController.navigateToSwipe(
                    userData = user
                )
            },
            onNavigateToEditProfile = {
                navController.navigateToProfile(
                    userData = it
                )
            },
            onNavigateToIntroProfile = {
                navController.currentBackStackEntry?.savedStateHandle?.set(userDetailsKey, it)
                navController.navigateToIntroProfile(
                    userData = it
                )
            }
        )
    }
}