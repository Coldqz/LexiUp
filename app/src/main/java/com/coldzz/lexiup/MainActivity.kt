package com.coldzz.lexiup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.coldzz.lexiup.core.components.OnBoardingScreen
import com.coldzz.lexiup.core.navigation.BottomNavBar
import com.coldzz.lexiup.core.navigation.NavRoutes
import com.coldzz.lexiup.features.blocks.presentation.BlockCreatingScreen
import com.coldzz.lexiup.features.blocks.presentation.BlockWordsScreen
import com.coldzz.lexiup.features.blocks.presentation.ReviewBlockScreen
import com.coldzz.lexiup.features.blocks.presentation.WordBlockScreen
import com.coldzz.lexiup.features.quiz.presentation.PickQuizScreen
import com.coldzz.lexiup.features.stats.presentation.StatsScreen
import com.coldzz.lexiup.features.words.presentation.WordDetailsScreen
import com.coldzz.lexiup.features.words.presentation.WordListScreen
import com.coldzz.lexiup.ui.theme.LexiUpTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LexiUpTheme {
                // ViewModel for handling global app state such as onboarding status
                val viewModel: MainActivityViewModel = hiltViewModel()

                val navController = rememberNavController()

                // Observes whether the user has completed the onboarding process
                val isOnBoarded by viewModel.isOnBoarded.collectAsStateWithLifecycle()

                // keep until datastore return if onBoarding been seen
                splashScreen.setKeepOnScreenCondition {
                    isOnBoarded == null
                }

                // Flag to determine if the bottom navigation bar should be visible based on current destination
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                // list of screens where we want to show bottom navigation
                val bottomNavScreens = listOf(
                    NavRoutes.LearningScreen.toString(),
                    NavRoutes.WordsScreen.toString(),
                    NavRoutes.StatsScreen.toString()
                )
                // return true if our current route is in the list
                val currentRouteIsInList = bottomNavScreens.any {
                    currentRoute?.contains(it) == true
                }

                val showBottomNavigation = currentRoute != null && currentRouteIsInList

                if (isOnBoarded == null) {
                    return@LexiUpTheme
                }

                // Determines the initial screen of the app based on onboarding status
                val startDestination =
                    if (isOnBoarded == false) {
                        NavRoutes.OnBoardingScreen
                    } else {
                        NavRoutes.LearningScreen
                    }

                val mainScreen = NavRoutes.LearningScreen
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (showBottomNavigation) {
                            BottomNavBar(navController = navController)
                        }
                    }

                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = startDestination,
                        modifier = Modifier
                            .padding(innerPadding)
                            .consumeWindowInsets(innerPadding)
                    ) {
                        composable<NavRoutes.StatsScreen> {
                            StatsScreen(navController = navController)
                        }
                        composable<NavRoutes.LearningScreen> {
                            WordBlockScreen(navController = navController)
                        }
                        composable<NavRoutes.WordsScreen> {
                            WordListScreen(navController = navController)
                        }
                        composable<NavRoutes.ReviewBlock> {
                            ReviewBlockScreen(navController = navController)
                        }
                        composable<NavRoutes.BlockCreatingScreen> {
                            BlockCreatingScreen(navController = navController)
                        }
                        composable<NavRoutes.BlockWordsList> {
                            BlockWordsScreen(navController = navController)
                        }
                        composable<NavRoutes.WordDetailsScreen> {
                            WordDetailsScreen(navController = navController)
                        }
                        composable<NavRoutes.PickQuizScreen> {
                            PickQuizScreen(navController = navController)
                        }
                        composable<NavRoutes.OnBoardingScreen> {
                            OnBoardingScreen {
                                if (navController.previousBackStackEntry != null) {
                                    navController.popBackStack()
                                } else {
                                    navController.navigate(mainScreen) {
                                        popUpTo(NavRoutes.OnBoardingScreen) { inclusive = true }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}