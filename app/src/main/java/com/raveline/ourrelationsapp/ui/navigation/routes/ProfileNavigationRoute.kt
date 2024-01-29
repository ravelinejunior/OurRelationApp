package com.raveline.ourrelationsapp.ui.navigation.routes

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.raveline.ourrelationsapp.ui.domain.models.UserDataModel
import com.raveline.ourrelationsapp.ui.screen.profileScreen.ProfileScreen
import com.raveline.ourrelationsapp.ui.screen.profileScreen.components.ProfileIntro
import com.raveline.ourrelationsapp.ui.viewmodel.AuthenticationViewModel
import com.raveline.ourrelationsapp.ui.viewmodel.AuthenticationViewModel.Companion.mUser

const val profileNavigationRoute = "profile_route"
const val profileIntroNavigationRoute = "profile_intro_route"

fun NavGraphBuilder.profileNavigationRoute(
    onSignOut: () -> Unit,
    navigateToEditProfile: (UserDataModel) -> Unit,
    navController: NavController
) {
    composable(
        route = profileIntroNavigationRoute
    ) {
        val firebaseAuth = FirebaseAuth.getInstance()
        val viewModel: AuthenticationViewModel = hiltViewModel()
        val userDataModel by viewModel.userState.collectAsState()
        navController.previousBackStackEntry?.savedStateHandle?.get<UserDataModel>(
            userDetailsKey
        )?.let {
            LaunchedEffect(viewModel.userState) {
                viewModel.userState.collect {
                    if (firebaseAuth.currentUser == null) {
                        onSignOut()
                    }
                }
            }

            ProfileIntro(
                userDataModel = userDataModel,
                navigateToEditProfile = navigateToEditProfile,
                onSignOut = {
                    viewModel.signOut()
                },
            )
        } ?: LaunchedEffect(viewModel.userState) {
            viewModel.userState.collect {
                if (firebaseAuth.currentUser == null) {
                    onSignOut()
                }
            }
        }

        ProfileIntro(
            userDataModel = mUser,
            navigateToEditProfile = navigateToEditProfile,
            onSignOut = {
                viewModel.signOut()
            },
        )
    }

    composable(
        profileNavigationRoute,
    ) {
        navController.previousBackStackEntry?.savedStateHandle?.get<UserDataModel>(
            userDetailsKey
        )?.let {
            val viewModel: AuthenticationViewModel = hiltViewModel()
            ProfileScreen(
                vm = viewModel,
                userData = it,
            )
        }
    }

}

fun NavController.navigateToIntroProfile(
    navOptions: NavOptions? = null,
    userData: UserDataModel?
) {
    currentBackStackEntry?.savedStateHandle?.set(userDetailsKey, userData)
    navigate(profileIntroNavigationRoute, navOptions)
}

fun NavController.navigateToProfile(
    navOptions: NavOptions? = null,
    userData: UserDataModel?
) {
    currentBackStackEntry?.savedStateHandle?.set(userDetailsKey, userData)
    navigate(profileNavigationRoute, navOptions)
}
