package com.coldzz.lexiup.features.stats.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.coldzz.lexiup.core.common.ResultUiState
import com.coldzz.lexiup.core.components.LexiUpErrorDialog
import com.coldzz.lexiup.core.components.LoadingStateIndicator
import com.coldzz.lexiup.features.stats.presentation.components.StatsScreenComponent
import com.coldzz.lexiup.features.stats.presentation.viewModel.StatsScreenViewModel

@Composable
fun StatsScreen(
    statsScreenViewModel: StatsScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState by statsScreenViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        statsScreenViewModel.events.collect { event ->
            when (event) {
                StatsScreenEvent.CloseScreen -> {
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
            val uiState = (uiState as ResultUiState.Success<StatsScreenUiState>).data
            StatsScreenComponent(
                percentage = uiState.learnedPercentage,
                currentWordsNumber = uiState.currentlyLearnedWords,
                remainingWordsNumber = uiState.remainingWords,
                totalWordsNumber = uiState.totalWordsNumber,
                levelProgressDataModel = uiState.levelProgressDataModel
            )
        }

        is ResultUiState.Error -> {
            val throwable = (uiState as ResultUiState.Error).throwable
            LexiUpErrorDialog(
                error = throwable,
                onRetry = { statsScreenViewModel.loadData() },
                onExit = { statsScreenViewModel.closeScreen() }
            )
        }
    }
}