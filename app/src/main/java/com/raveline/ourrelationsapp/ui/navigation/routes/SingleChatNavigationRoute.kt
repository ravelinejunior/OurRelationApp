package com.raveline.ourrelationsapp.ui.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.raveline.ourrelationsapp.ui.screen.singleChatScreen.SingleChatScreen

const val singleChatNavigationRoute = "SingleChatRoute"

fun NavGraphBuilder.singleChatNavigationRoute() {
    composable(singleChatNavigationRoute) {
        SingleChatScreen()
    }
}

fun NavController.navigateSingleChat(
    navOptions: NavOptions? = null
) {
    navigate(singleChatNavigationRoute, navOptions)
}