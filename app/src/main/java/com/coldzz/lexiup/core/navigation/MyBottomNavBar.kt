package com.coldzz.lexiup.core.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState

/**
 * Bottom navigation bar that handles top-level destination switching.
 */
@Composable
fun BottomNavBar(modifier: Modifier = Modifier, navController: NavController) {
    val items = listOf(
        NavRoutes.StatsScreen,
        NavRoutes.LearningScreen,
        NavRoutes.WordsScreen
    )

    // Observe the current back stack entry to stay in sync with the navigation state
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination


    NavigationBar(modifier = modifier) {
        items.forEach { item ->

            // Check if the current destination is part of the navigation hierarchy for this item.
            // This ensures the item stays selected even when navigating to nested screens.
            val selected = currentDestination?.hierarchy?.any {
                it.route?.contains(item.toString()) == true
            } == true

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }

                        // Avoid multiple copies of the same destination when
                        // reelecting the same item (prevents ViewModel duplication)
                        launchSingleTop = true

                        // Restore state when reelecting a previously selected item
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(item.iconRes),
                        contentDescription = item.name
                    )
                },
                label = {
                    Text(item.name)
                }
            )
        }
    }
}