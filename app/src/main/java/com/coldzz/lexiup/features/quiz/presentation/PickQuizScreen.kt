package com.coldzz.lexiup.features.quiz.presentation

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
import com.coldzz.lexiup.features.quiz.presentation.components.PickQuizScreenComponent
import com.coldzz.lexiup.features.quiz.presentation.viewmodel.PickQuizViewModel

@Composable
fun PickQuizScreen(
    navController: NavController,
    viewModel: PickQuizViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is QuizEvent.ShowToast -> {
                    Toast.makeText(
                        context, event.message, Toast.LENGTH_SHORT
                    ).show()
                }

                is QuizEvent.CloseQuiz -> {
                    navController.popBackStack()
                }
            }
        }
    }

    when (uiState) {
        is ResultUiState.Loading -> {
            LoadingStateIndicator()
        }

        is ResultUiState.Error -> {
            val throwable = (uiState as ResultUiState.Error).throwable
            LexiUpErrorDialog(
                error = throwable,
                onRetry = { viewModel.loadData() },
                onExit = { viewModel.closeQuiz() }
            )
        }

        is ResultUiState.Success -> {
            val data = (uiState as ResultUiState.Success<QuizUiState>).data
            PickQuizScreenComponent(
                answerOptions = data.choices,
                definition = data.definition,
                partOfSpeech = data.partOfSpeech,
                currentProgressValue = data.currentProgressValue,
                maxProgressValue = data.maxProgressValue,
                showCongratulationDialog = data.showCongratulationDialog,
                skippedWords = data.skippedWords,
                onDialogButtonClick = { viewModel.closeQuiz() },
                onOptionClick = { wordId -> viewModel.checkAnswer(wordId) },
                onCloseButtonAction = { viewModel.closeQuiz() }
            )
        }
    }
}