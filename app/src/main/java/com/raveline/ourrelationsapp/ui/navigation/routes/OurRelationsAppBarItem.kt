package com.raveline.ourrelationsapp.ui.navigation.routes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.MailOutline
import androidx.compose.material.icons.rounded.PersonOutline
import androidx.compose.ui.graphics.vector.ImageVector

sealed class OurRelationsAppBarItem(
    val label: String,
    val icon: ImageVector,
    val destination: String,
    val position: Int,
) {
    data object ProfileItemBar : OurRelationsAppBarItem(
        label = "Profile",
        icon = Icons.Rounded.PersonOutline,
        destination = profileIntroNavigationRoute,
        position = 0,
    )

    data object SwipeItemBar : OurRelationsAppBarItem(
        label = "Swipe",
        icon = Icons.Rounded.FavoriteBorder,
        destination = swipeNavigationRoute,
        position = 1,
    )

    data object ChatListItemBar : OurRelationsAppBarItem(
        label = "Chat List",
        icon = Icons.Rounded.MailOutline,
        destination = chatListNavigationRoute,
        position = 2,
    )
}

val bottomAppBarItems = listOf(
    OurRelationsAppBarItem.ProfileItemBar,
    OurRelationsAppBarItem.SwipeItemBar,
    OurRelationsAppBarItem.ChatListItemBar
)