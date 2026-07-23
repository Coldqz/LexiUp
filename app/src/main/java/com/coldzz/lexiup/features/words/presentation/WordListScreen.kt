package com.coldzz.lexiup.features.words.presentation

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
import com.coldzz.lexiup.features.words.presentation.components.WordListComponentMode
import com.coldzz.lexiup.features.words.presentation.components.WordListScreenComponent
import com.coldzz.lexiup.features.words.presentation.viewmodel.WordsListViewModel

@Composable
fun WordListScreen(
    navController: NavController,
    wordsListViewModel: WordsListViewModel = hiltViewModel()
) {

    val uiState by wordsListViewModel.wordsList.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        wordsListViewModel.events.collect { event ->
            when (event) {
                WordListEvent.CloseScreen -> {
                    navController.popBackStack()
                }
            }
        }
    }

    @Composable
    fun LaunchScreen(data: List<WordItemUiModel>) {
        WordListScreenComponent(
            wordsList = data,
            searchBarListMode = WordListComponentMode.BookmarkMode(
                actionAddToReviewBlock = { wordId ->
                    wordsListViewModel.addWordToReviewBlock(wordId)
                },
                actionRemoveFromReviewBlock = { wordId ->
                    wordsListViewModel.removeWordFromReviewBlock(wordId)
                }
            ),
            actionOnElementClick = { wordId ->
                navController.navigate(NavRoutes.WordDetailsScreen(wordId))
            },
            listMode = WordListComponentMode.BookmarkMode(
                actionAddToReviewBlock = { wordId ->
                    wordsListViewModel.addWordToReviewBlock(wordId)
                },
                actionRemoveFromReviewBlock = { wordId ->
                    wordsListViewModel.removeWordFromReviewBlock(wordId)
                }
            )
        )
    }

    when (uiState) {
        ResultUiState.Loading -> {
            LoadingStateIndicator()
        }

        is ResultUiState.Success -> {
            LaunchScreen((uiState as ResultUiState.Success).data)
        }

        is ResultUiState.Error -> {
            val throwable = (uiState as ResultUiState.Error).throwable
            LexiUpErrorDialog(
                error = throwable,
                onRetry = { wordsListViewModel.loadData() },
                onExit = { wordsListViewModel.closeScreen() }
            )
        }
    }

}