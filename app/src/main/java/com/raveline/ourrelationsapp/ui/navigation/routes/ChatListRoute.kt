package com.raveline.ourrelationsapp.ui.navigation.routes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.raveline.ourrelationsapp.ui.screen.chatListScreen.ChatListScreen

const val chatListNavigationRoute = "ChatListRoute"

fun NavGraphBuilder.chatListRoute() {
    composable(chatListNavigationRoute) {
        ChatListScreen()
    }
}

fun NavController.navigateToChatList(
    navOptions: NavOptions? = null
) {
    navigate(chatListNavigationRoute, navOptions)
}