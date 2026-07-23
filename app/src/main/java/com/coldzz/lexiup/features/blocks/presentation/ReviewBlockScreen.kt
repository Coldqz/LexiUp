package com.coldzz.lexiup.features.blocks.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.coldzz.lexiup.core.common.ResultUiState
import com.coldzz.lexiup.core.components.LexiUpErrorDialog
import com.coldzz.lexiup.core.components.LoadingStateIndicator
import com.coldzz.lexiup.core.navigation.NavRoutes
import com.coldzz.lexiup.features.blocks.presentation.components.ReviewBlockScreenComponent
import com.coldzz.lexiup.features.blocks.presentation.viewmodel.ReviewBlockScreenViewModel

@Composable
fun ReviewBlockScreen(
    reviewViewModel: ReviewBlockScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState by reviewViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        reviewViewModel.events.collect { event ->
            when (event) {
                ReviewBlockEvent.CloseScreen -> {
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
            val data = (uiState as ResultUiState.Success).data
            ReviewBlockScreenComponent(
                wordsList = data.reviewWords,
                searchBarList = data.allWords,
                actionOnElementClick = { wordId ->
                    navController.navigate(NavRoutes.WordDetailsScreen(wordId))
                },
                addWordToReviewBlock = { wordId ->
                    reviewViewModel.addWordToReviewBlock(wordId)
                },
                removeWordFromReviewBlock = { wordId ->
                    reviewViewModel.removeWordFromReviewBlock(wordId)
                },
                onStartButtonClick = {
                    navController.navigate(NavRoutes.PickQuizScreen(blockId = data.reviewBlockId, saveProgressChanges = false))
                }
            )
        }

        is ResultUiState.Error -> {
            val throwable = (uiState as ResultUiState.Error).throwable
            LexiUpErrorDialog(
                error = throwable,
                onRetry = { reviewViewModel.loadData() },
                onExit = { reviewViewModel.closeScreen() }
            )
        }
    }
}