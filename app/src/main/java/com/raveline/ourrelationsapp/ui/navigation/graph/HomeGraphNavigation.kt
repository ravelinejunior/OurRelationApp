package com.raveline.ourrelationsapp.ui.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.raveline.ourrelationsapp.ui.navigation.routes.chatListRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.loginNavigationRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.profileNavigationRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.signupRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.singleChatNavigationRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.swipeNavigationRoute


const val homeGraphRoute = "HomeGraph"

fun NavGraphBuilder.homeGraph() {
    navigation(
        startDestination = loginNavigationRoute,
        route = homeGraphRoute
    ) {
        loginNavigationRoute()
        signupRoute()
        chatListRoute()
        profileNavigationRoute()
        singleChatNavigationRoute()
        swipeNavigationRoute()
    }
}