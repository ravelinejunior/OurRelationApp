package com.raveline.ourrelationsapp

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.raveline.ourrelationsapp.ui.navigation.graph.navigateSingleTopWithPopUpTo
import com.raveline.ourrelationsapp.ui.navigation.navHost.OurRelationsNavHost
import com.raveline.ourrelationsapp.ui.navigation.routes.OurRelationsAppBarItem
import com.raveline.ourrelationsapp.ui.navigation.routes.bottomAppBarItems
import com.raveline.ourrelationsapp.ui.navigation.routes.chatListNavigationRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.loginNavigationRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.profileNavigationRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.signupNavigationRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.splashNavigationRoute
import com.raveline.ourrelationsapp.ui.navigation.routes.swipeNavigationRoute
import com.raveline.ourrelationsapp.ui.screen.components.OurRelationsAppBar
import com.raveline.ourrelationsapp.ui.screen.swipeScreen.SwipeScreen
import com.raveline.ourrelationsapp.ui.theme.OurRelationsAppTheme
import dagger.hilt.android.AndroidEntryPoint

private val TAG: String = MainActivity::class.java.simpleName

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            OurRelationsAppTheme {
                val systemUiController = rememberSystemUiController()
                val isSystemInDarkMode = isSystemInDarkTheme()
                val window: Window = this.window
                window.navigationBarColor = MaterialTheme.colorScheme.background.toArgb()

                if (isSystemInDarkMode) {
                    systemUiController.setSystemBarsColor(
                        color = MaterialTheme.colorScheme.primaryContainer
                    )
                } else {
                    systemUiController.setSystemBarsColor(
                        color = Color.Transparent
                    )
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    OurRelationsApp()
                }
            }
        }
    }
}

@Composable
fun OurRelationsApp(
    navController: NavHostController = rememberNavController()
) {
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
            profileNavigationRoute -> OurRelationsAppBarItem.ProfileItemBar
            swipeNavigationRoute -> OurRelationsAppBarItem.SwipeItemBar
            chatListNavigationRoute -> OurRelationsAppBarItem.ChatListItemBar
            else -> OurRelationsAppBarItem.SwipeItemBar
        }

        mutableStateOf(item)
    }

    val isBottomBarVisible = remember(backStackEntryState) {
        backStackEntryState?.destination?.route != loginNavigationRoute &&
                backStackEntryState?.destination?.route != splashNavigationRoute &&
                backStackEntryState?.destination?.route != signupNavigationRoute

    }

    Scaffold(
        modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer),
        bottomBar = {
            if (isBottomBarVisible) {
                OurRelationsAppBar(
                    mItem = selectedItem,
                    items = bottomAppBarItems,
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


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun GreetingPreview() {
    OurRelationsAppTheme {
        SwipeScreen { _ -> }
    }
}