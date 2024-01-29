package com.raveline.ourrelationsapp

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.raveline.ourrelationsapp.ui.navigation.graph.NewsNavigator
import com.raveline.ourrelationsapp.ui.screen.swipeScreen.SwipeScreen
import com.raveline.ourrelationsapp.ui.theme.OurRelationsAppTheme
import com.raveline.ourrelationsapp.ui.viewmodel.AuthenticationViewModel
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
                    val viewModel = viewModel(modelClass = AuthenticationViewModel::class.java)
                    OurRelationsApp(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun OurRelationsApp(
    navController: NavHostController = rememberNavController(),
    viewModel: AuthenticationViewModel
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        NewsNavigator(
            viewModel
        )
    }
    /* // See the change in navigation
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
                 OurRelationsBottomAppBar(
                     mItem = selectedItem,
                     items = bottomAppBarItems,
                     selectedItem = selectedItem.position,
                     onItemChanged = { item ->
                         navController.navigateSingleTopWithPopUpTo(item,viewModel.userData)
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
     }*/

}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun GreetingPreview() {
    OurRelationsAppTheme {
        SwipeScreen(
            onNavigateToSwipe = {},
        )
    }
}