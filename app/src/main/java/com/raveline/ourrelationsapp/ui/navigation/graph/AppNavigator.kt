package com.raveline.ourrelationsapp.ui.navigation.graph

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.raveline.ourrelationsapp.ui.navigation.navHost.OurRelationsNavHost
import com.raveline.ourrelationsapp.ui.navigation.routes.OurRelationsAppBarItem
import com.raveline.ourrelationsapp.ui.navigation.routes.bottomAppBarItems
import com.raveline.ourrelationsapp.ui.navigation.routes.chatListNavigationRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.loginNavigationRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.profileIntroNavigationRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.profileNavigationRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.signupNavigationRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.splashNavigationRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.swipeNavigationRoute
import com.raveline.ourrelationsapp.ui.screen.components.OurRelationsAppBar

internal const val articleModelKey = "article"


/**
 * `NewsNavigator` is a composable function that manages the navigation of the news application.
 * It's annotated with `@Composable`, indicating that it's a composable function that describes part of the UI.
 *
 * @property bottomNavigationItems A list of items to be displayed in the bottom navigation bar.
 * @property navController A `NavController` that manages app navigation.
 * @property backStackState The current state of the navigation back stack.
 * @property selectedItem The index of the currently selected item in the bottom navigation bar.
 * @property isBottomBarVisible A boolean value that determines whether the bottom navigation bar should be visible.
 *
 * @method NewsNavigator This function sets up the navigation for the application.
 * @method Scaffold This function provides a framework that materializes the Material Design specification.
 * @method NavHost This function sets up a navigation graph within the application.
 */

private val TAG: String = "TAGAppNavigator"

@RequiresApi(Build.VERSION_CODES.O)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun NewsNavigator() {
    val navController = rememberNavController()
    // See the change in navigation
    LaunchedEffect(Unit) {
        navController.addOnDestinationChangedListener { _, _, _ ->
            val routes = navController.graph.map {
                it.route
            }
            Log.i(TAG, "Back stack routes: $routes")
        }
    }

    //Saved state
    val backStackEntryState by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntryState?.destination
    val currentRoute = currentDestination?.route

    val selectedItem by remember(currentDestination) {

        val item = when (currentRoute) {
            profileIntroNavigationRoute -> OurRelationsAppBarItem.ProfileItemBar
            swipeNavigationRoute -> OurRelationsAppBarItem.SwipeItemBar
            chatListNavigationRoute -> OurRelationsAppBarItem.ChatListItemBar
            else -> OurRelationsAppBarItem.SwipeItemBar
        }

        mutableStateOf(item)
    }

    val isBottomBarVisible = remember(backStackEntryState) {
        backStackEntryState?.destination?.route != loginNavigationRoute &&
                backStackEntryState?.destination?.route != splashNavigationRoute &&
                backStackEntryState?.destination?.route != signupNavigationRoute &&
                backStackEntryState?.destination?.route != profileNavigationRoute

    }

    Scaffold(
        modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer),
        bottomBar = {
            if (isBottomBarVisible) {
                OurRelationsAppBar(
                    mItem = selectedItem,
                    items = bottomAppBarItems,
                    selectedItem = selectedItem.position,
                    onItemChanged = { item ->
                        navController.navigateSingleTopWithPopUpTo(item)
                    },
                    modifier =
                    Modifier
                        .background(
                            color = Color.Red
                        )
                        .semantics {
                            testTag = "OurRelationsAppBar"
                        }
                )
            }
        }
    ) {

        Box(modifier = Modifier.padding(it)) {
            OurRelationsNavHost(navController = navController)
        }
    }
}




