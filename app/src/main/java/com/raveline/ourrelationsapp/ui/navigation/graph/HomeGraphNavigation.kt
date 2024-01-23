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
import com.raveline.ourrelationsapp.ui.navigation.routes.navigateToProfile
import com.raveline.ourrelationsapp.ui.navigation.routes.navigateToSwipe
import com.raveline.ourrelationsapp.ui.navigation.routes.profileNavigationRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.signupRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.singleChatNavigationRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.splashNavigationRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.splashRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.swipeNavigationRoute


const val homeGraphRoute = "HomeGraph"

fun NavGraphBuilder.homeGraph(
    onNavigateToLogin: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onNavigateToSignOut: () -> Unit,
    onNavigateToHome: (UserDataModel) -> Unit,
    onNavigateToSwipe: (UserDataModel) -> Unit,
) {
    navigation(
        startDestination = splashNavigationRoute,
        route = homeGraphRoute
    ) {
        chatListRoute()
        profileNavigationRoute(
            onSignOut = onNavigateToSignOut
        )
        singleChatNavigationRoute()
        swipeNavigationRoute(
            onNavigateToSwipe
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

fun NavController.navigateSingleTopWithPopUpTo(
    item: OurRelationsAppBarItem,
) {
    val (route, navigate) = when (item) {

        OurRelationsAppBarItem.ProfileItemBar -> Pair(
            profileNavigationRoute, ::navigateToProfile
        )

        OurRelationsAppBarItem.SwipeItemBar -> Pair(
            swipeNavigationRoute, ::navigateToSwipe
        )

        OurRelationsAppBarItem.ChatListItemBar -> Pair(
            chatListNavigationRoute, ::navigateToChatList
        )
    }

    val navOptions = navOptions {
        launchSingleTop = true
        popUpTo(route)
    }

    navigate(navOptions)
}

