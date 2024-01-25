package com.raveline.ourrelationsapp.ui.navigation.routes

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.raveline.ourrelationsapp.ui.screen.profileScreen.ProfileScreen
import com.raveline.ourrelationsapp.ui.screen.profileScreen.components.ProfileIntro
import com.raveline.ourrelationsapp.ui.viewmodel.AuthenticationViewModel

const val profileNavigationRoute = "profile_route"
const val profileIntroNavigationRoute = "profile_intro_route"

fun NavGraphBuilder.profileNavigationRoute(
    onSignOut: () -> Unit,
) {

    composable(
        route = profileIntroNavigationRoute
    ) {
        val viewModel: AuthenticationViewModel = hiltViewModel()
        val userDataModel by viewModel.userState.collectAsState()

        LaunchedEffect(viewModel.userState) {
            viewModel.userState.collect { user ->
                if (user != null) {
                    Log.i("TAGProfileNavigation", "profileNavigationRoute: $user")
                } else {
                    onSignOut()
                }
            }
        }


        ProfileIntro(
            viewModel = viewModel,
            userDataModel = userDataModel,
            onSignOut = {
                viewModel.signOut()
            })

    }

    composable(profileNavigationRoute) {
        val viewModel: AuthenticationViewModel = hiltViewModel()
        ProfileScreen(
            authenticationViewModel = viewModel,
            onSignOut = onSignOut
        )
    }
}

fun NavController.navigateToIntroProfile(
    navOptions: NavOptions? = null,
) {
    navigate(profileIntroNavigationRoute, navOptions)
}

fun NavController.navigateToProfile(
    navOptions: NavOptions? = null,
) {
    navigate(profileNavigationRoute, navOptions)
}