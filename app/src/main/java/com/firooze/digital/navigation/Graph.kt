package com.firooze.digital.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.firooze.digital.screen.home.HomeScreen
import com.firooze.digital.screen.newsDetail.NewsDetailScreen
import kotlinx.coroutines.CoroutineScope

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    startDestination: String = NavigationItem.Home.route,
    actions: NavActions = NavActions(navController)
) {

    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: startDestination

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        homeScreen(this, actions)
        newsDetailScreen(this, actions)
    }

}

fun homeScreen(
    builder: NavGraphBuilder,
    actions: NavActions,
) {
    builder.apply {
        composable(
            route = NavigationItem.Home.route,
        ) {
            HomeScreen(onNavigateToDetail = { actions.navigateToNewsDetail(it) })

        }
    }
}

fun newsDetailScreen(
    builder: NavGraphBuilder,
    actions: NavActions,

    ) {
    builder.apply {
        composable(
            route = "${NavigationItem.Detail.route}?$ID={$ID}",
            arguments = listOf(navArgument(name = ID) {
                type = NavType.StringType
            })
        ) {
            NewsDetailScreen(onNavigateToParent = { actions.navigateUpFromDetailToHome() })

        }
    }
}