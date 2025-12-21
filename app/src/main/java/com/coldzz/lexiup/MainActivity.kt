package com.coldzz.lexiup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.coldzz.lexiup.features.blocks.presentation.BlockCreatingScreen
import com.coldzz.lexiup.features.blocks.presentation.ReviewBlockScreen
import com.coldzz.lexiup.features.blocks.presentation.WordBlockScreen
import com.coldzz.lexiup.features.navigation.BottomNavBar
import com.coldzz.lexiup.features.navigation.NavRoutes
import com.coldzz.lexiup.features.profile.presentation.ProfileScreen
import com.coldzz.lexiup.features.words.presentation.WordListScreen
import com.coldzz.lexiup.ui.theme.LexiUpTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LexiUpTheme {
                val startDestination = NavRoutes.HomeScreen
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavBar(
                            navController = navController,
                            startDestination = startDestination
                        )
                    }

                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = startDestination,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<NavRoutes.HomeScreen> {
                            ProfileScreen()
                        }
                        composable<NavRoutes.LearningScreen> {
                            WordBlockScreen(navController = navController)
                        }
                        composable<NavRoutes.WordsScreen> {
                            WordListScreen()
                        }
                        composable<NavRoutes.ReviewBlock> {
                            ReviewBlockScreen()
                        }
                        composable<NavRoutes.BlockCreatingScreen> {
                            BlockCreatingScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LexiUpTheme {
        Greeting("Android")
    }
}