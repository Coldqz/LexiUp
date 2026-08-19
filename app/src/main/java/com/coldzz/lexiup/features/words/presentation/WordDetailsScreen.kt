package com.coldzz.lexiup.features.words.presentation

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
import com.coldzz.lexiup.features.words.presentation.components.WordDetailsComponent
import com.coldzz.lexiup.features.words.presentation.viewmodel.WordDetailsViewModel

@Composable
fun WordDetailsScreen(
    navController: NavController,
    wordDetailsViewModel: WordDetailsViewModel = hiltViewModel()
) {
    val uiState by wordDetailsViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        wordDetailsViewModel.events.collect { event ->
            when (event) {
                is WordDetailsEvent.ShowToast -> {
                    Toast.makeText(
                        context, event.message, Toast.LENGTH_SHORT
                    ).show()
                }
                WordDetailsEvent.CloseScreen -> {
                    navController.popBackStack()
                }
            }
        }
    }

    when (uiState) {
        is ResultUiState.Loading -> {
            LoadingStateIndicator()
        }


        is ResultUiState.Success -> {
            WordDetailsComponent(
                uiState = (uiState as ResultUiState.Success<WordDetailsUiState>).data,
                onBackButtonAction = { navController.popBackStack() },
                actionAddToReviewBlock = { wordId ->
                    wordDetailsViewModel.addWordToReviewBlock(wordId)
                },
                actionRemoveFromReviewBlock = { wordId ->
                    wordDetailsViewModel.removeWordFromReviewBlock(wordId)
                },
                actionOnPlayButton = { audioUrl ->
                    wordDetailsViewModel.playAudio(audioUrl)
                }
            )
        }

        is ResultUiState.Error -> {
            val throwable = (uiState as ResultUiState.Error).throwable
            LexiUpErrorDialog(
                error = throwable,
                onRetry = { wordDetailsViewModel.loadData() },
                onExit = { wordDetailsViewModel.closeScreen() }
            )
        }
    }
}