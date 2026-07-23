package com.coldzz.lexiup.features.blocks.presentation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.coldzz.lexiup.core.common.ResultUiState
import com.coldzz.lexiup.core.components.LexiUpErrorDialog
import com.coldzz.lexiup.core.components.LoadingStateIndicator
import com.coldzz.lexiup.core.navigation.NavRoutes
import com.coldzz.lexiup.features.blocks.presentation.components.WordBlockScreenComponent
import com.coldzz.lexiup.features.blocks.presentation.viewmodel.WordBlockViewModel

@Composable
fun WordBlockScreen(
    wordBlockViewModel: WordBlockViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState by wordBlockViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        wordBlockViewModel.event.collect { event ->
            when (event) {
                is WordBlockEvent.ShowToast -> {
                    Toast.makeText(
                        context, event.message, Toast.LENGTH_SHORT
                    ).show()
                }

                WordBlockEvent.CloseScreen -> {
                    navController.popBackStack()
                }
            }
        }
    }

    when (uiState) {
        ResultUiState.Loading -> {
            LoadingStateIndicator()
        }

        is ResultUiState.Success -> {
            WordBlockScreenComponent(
                actionOnInfoButton = {
                    navController.navigate(NavRoutes.OnBoardingScreen)
                },
                actionOnBookmarkButton = {
                    navController.navigate(NavRoutes.ReviewBlock)
                },
                actionOnBlockClick = { blockId ->
                    navController.navigate(NavRoutes.BlockWordsList(blockId))
                },
                actionOnActiveStartButton = { blockId ->
                    navController.navigate(NavRoutes.PickQuizScreen(blockId))
                },
                actionOnActiveBlockDeactivate = { blockId ->
                    wordBlockViewModel.deactivateBlock(blockId)
                },
                actionOnActiveBlockDelete = { blockId ->
                    wordBlockViewModel.deleteBlock(blockId)
                },
                actionOnFloatingActionButton = {
                    navController.navigate(NavRoutes.BlockCreatingScreen)
                },
                actionOnPlannedBlockDelete = { blockId ->
                    wordBlockViewModel.deleteBlock(blockId)
                },
                actionOnPlannedBlockActivate = { blockId ->
                    wordBlockViewModel.activateBlock(blockId)
                },
                actionOnLearnedBlockRepeat = { blockId ->
                    navController.navigate(
                        NavRoutes.PickQuizScreen(
                            blockId = blockId,
                            saveProgressChanges = false
                        )
                    )
                },
                uiState = (uiState as ResultUiState.Success<BlocksScreenUiState>).data
            )
        }

        is ResultUiState.Error -> {
            val throwable = (uiState as ResultUiState.Error).throwable
            LexiUpErrorDialog(
                error = throwable,
                onRetry = { wordBlockViewModel.loadData() },
                onExit = { wordBlockViewModel.closeScreen() }
            )
        }
    }
}