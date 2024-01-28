package com.raveline.ourrelationsapp.ui.navigation.routes

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.raveline.ourrelationsapp.ui.domain.models.UserDataModel
import com.raveline.ourrelationsapp.ui.screen.profileScreen.ProfileScreen
import com.raveline.ourrelationsapp.ui.screen.profileScreen.components.ProfileIntro
import com.raveline.ourrelationsapp.ui.viewmodel.AuthenticationViewModel

const val profileNavigationRoute = "profile_route"
const val profileIntroNavigationRoute = "profile_intro_route"

fun NavGraphBuilder.profileNavigationRoute(
    onSignOut: () -> Unit,
    navigateToEditProfile: (UserDataModel) -> Unit
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
            userDataModel = userDataModel,
            onSignOut = {
                viewModel.signOut()
            },
            navigateToEditProfile = navigateToEditProfile
        )
    }

    composable(
        "$profileNavigationRoute/{$userDetailsKey}={}",
        arguments = listOf(
            navArgument(userDetailsKey) {
                type = NavType.ParcelableType(UserDataModel::class.java)
            }
        )
    ) { backStackEntry ->
        val userData =
            backStackEntry.arguments?.getParcelable<UserDataModel>(userDetailsKey)
        val viewModel: AuthenticationViewModel = hiltViewModel()
        val user by viewModel.userState.collectAsState()
        ProfileScreen(
            vm = viewModel,
            userData = userData,
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
    userData: UserDataModel?
) {
    navigate("$profileNavigationRoute/{$userDetailsKey}={$userData}", navOptions)
}

fun profileScreenRoute(userId: String) = "profile/$userId"