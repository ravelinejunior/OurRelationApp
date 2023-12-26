package com.raveline.ourrelationsapp.ui.navigation.navHost

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.raveline.ourrelationsapp.ui.navigation.graph.homeGraph
import com.raveline.ourrelationsapp.ui.navigation.graph.homeGraphRoute


@Composable
fun OurRelationsNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = homeGraphRoute
    ) {
        homeGraph()
    }
}