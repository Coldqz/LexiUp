package com.coldzz.lexiup.features.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.coldzz.lexiup.ui.theme.LexiUpTheme

@Composable
fun BottomNavBar(modifier: Modifier = Modifier, navController: NavController, startDestination: NavRoutes) {
    val items = listOf(
        NavRoutes.HomeScreen,
        NavRoutes.LearningScreen,
        NavRoutes.WordsScreen
    )

    // searching for index of start destination passed in composable argument
    var selectedNavigationIndex by rememberSaveable {
        mutableIntStateOf(items.indexOf(startDestination))
    }

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedNavigationIndex == index,
                onClick = {
                    navController.navigate(item)
                    selectedNavigationIndex = index
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

@Preview
@Composable
private fun MyBottomBarPreview() {
    LexiUpTheme {
        BottomNavBar(navController = rememberNavController(), startDestination = NavRoutes.HomeScreen)
    }
}