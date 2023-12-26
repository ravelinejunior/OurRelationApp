package com.raveline.ourrelationsapp.ui.navigation

import com.raveline.ourrelationsapp.ui.navigation.routes.chatListNavigationRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.loginNavigationRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.profileNavigationRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.swipeNavigationRoute
import com.raveline.ourrelationsapp.ui.screen.signupScreen.signupNavigationRoute
import com.raveline.ourrelationsapp.ui.screen.singleChatScreen.singleChatNavigationRoute

sealed class AppDestination(
    route: String
) {
    object ChatListScreenRoute : AppDestination(chatListNavigationRoute)
    object LoginScreenRoute : AppDestination(loginNavigationRoute)
    object ProfileScreenListRoute : AppDestination(profileNavigationRoute)
    object SignupScreenRoute : AppDestination(signupNavigationRoute)
    object SwipeScreenRoute : AppDestination(swipeNavigationRoute)
    object SingleChatScreenRoute : AppDestination(singleChatNavigationRoute) {
        fun createRouteWithArgs(id: String) = "singleChat/$id"
    }
}