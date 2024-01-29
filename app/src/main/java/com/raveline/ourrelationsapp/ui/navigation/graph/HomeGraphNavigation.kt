package com.raveline.ourrelationsapp.ui.navigation.graph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.raveline.ourrelationsapp.ui.domain.models.UserDataModel
import com.raveline.ourrelationsapp.ui.navigation.routes.OurRelationsAppBarItem
import com.raveline.ourrelationsapp.ui.navigation.routes.chatListNavigationRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.chatListRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.loginNavigationRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.navigateToChatList
import com.raveline.ourrelationsapp.ui.navigation.routes.navigateToIntroProfile
import com.raveline.ourrelationsapp.ui.navigation.routes.navigateToSwipe
import com.raveline.ourrelationsapp.ui.navigation.routes.profileIntroNavigationRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.profileNavigationRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.signupRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.singleChatNavigationRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.splashNavigationRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.splashRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.swipeNavigationRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.userDetailsKey

const val homeGraphRoute = "HomeGraph"

fun NavGraphBuilder.homeGraph(
    navController: NavController,
    onNavigateToLogin: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onNavigateToSignOut: () -> Unit,
    onNavigateToEditProfile: (UserDataModel) -> Unit,
    onNavigateToIntroProfile: (UserDataModel) -> Unit,
    onNavigateToHome: (UserDataModel) -> Unit,
    onNavigateToSwipe: (UserDataModel) -> Unit,
) {
    navigation(
        startDestination = splashNavigationRoute,
        route = homeGraphRoute
    ) {
        chatListRoute()
        profileNavigationRoute(
            onSignOut = onNavigateToSignOut,
            navigateToEditProfile = onNavigateToEditProfile,
            navController = navController
        )
        singleChatNavigationRoute()
        swipeNavigationRoute(
            onNavigateToSwipe,
        )
        loginNavigationRoute(
            onNavigateToHome = onNavigateToHome,
            onNavigateToSignUp = onNavigateToSignUp
        )
        signupRoute(
            onNavigateToHome = onNavigateToHome,
            onNavigateToLogin = onNavigateToLogin
        )
        splashRoute(
            onNavigateToHome = onNavigateToHome,
            onNavigateToLogin = onNavigateToLogin
        )
    }
}

fun navigateToTab(
    navController: NavController,
    route: String,
) {
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


fun NavController.navigateSingleTopWithPopUpTo(
    item: OurRelationsAppBarItem,
    userDataModel: UserDataModel?
) {
    when (item) {
        OurRelationsAppBarItem.ProfileItemBar -> {
            val navOptions = navOptions {
                launchSingleTop = true
                popUpTo(profileIntroNavigationRoute)
            }
            currentBackStackEntry?.savedStateHandle?.set(userDetailsKey, userDataModel)
            navigateToIntroProfile(navOptions,userDataModel)
        }

        OurRelationsAppBarItem.SwipeItemBar -> {
            val navOptions = navOptions {
                launchSingleTop = true
                popUpTo(swipeNavigationRoute)
            }
            currentBackStackEntry?.savedStateHandle?.set(userDetailsKey, userDataModel)
            navigateToSwipe(navOptions,userDataModel)
        }

        OurRelationsAppBarItem.ChatListItemBar -> {
            val navOptions = navOptions {
                launchSingleTop = true
                popUpTo(chatListNavigationRoute)
            }
            navigateToChatList(navOptions)
        }
    }
}

