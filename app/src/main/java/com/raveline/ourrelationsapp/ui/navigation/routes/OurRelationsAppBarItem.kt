package com.raveline.ourrelationsapp.ui.navigation.routes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.MailOutline
import androidx.compose.material.icons.rounded.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class OurRelationsAppBarItem (
    val label:String,
    val icon:ImageVector,
    val destination:String
){
    object ProfileItemBar : OurRelationsAppBarItem(
        label = "Profile",
        icon = Icons.Rounded.Person,
        destination = profileNavigationRoute
    )

    object SwipeItemBar : OurRelationsAppBarItem(
        label = "Swipe",
        icon = Icons.Rounded.Favorite,
        destination = swipeNavigationRoute
    )

    object ChatListItemBar : OurRelationsAppBarItem(
        label = "Chat List",
        icon = Icons.Rounded.MailOutline,
        destination = chatListNavigationRoute
    )
}

val bottomAppBarItems = listOf(
    OurRelationsAppBarItem.ProfileItemBar,
    OurRelationsAppBarItem.SwipeItemBar,
    OurRelationsAppBarItem.ChatListItemBar
)