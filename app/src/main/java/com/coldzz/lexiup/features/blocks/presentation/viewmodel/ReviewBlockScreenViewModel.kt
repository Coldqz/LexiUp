package com.coldzz.lexiup.features.blocks.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldzz.lexiup.core.common.ResultUiState
import com.coldzz.lexiup.features.blocks.domain.use_case.block_use_cases.WordBlockUseCases
import com.coldzz.lexiup.features.blocks.presentation.ReviewBlockEvent
import com.coldzz.lexiup.features.blocks.presentation.ReviewBlockUiState
import com.coldzz.lexiup.features.words.domain.use_case.WordUseCases
import com.coldzz.lexiup.features.words.presentation.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

private const val TAG = "ReviewBlockScreenViewModel"

@HiltViewModel
class ReviewBlockScreenViewModel @Inject constructor(
    private val blockUseCases: WordBlockUseCases,
    private val wordUseCases: WordUseCases
) : ViewModel() {

    private val _uiState: MutableStateFlow<ResultUiState<ReviewBlockUiState>> =
        MutableStateFlow(
            ResultUiState.Loading
        )
    val uiState: StateFlow<ResultUiState<ReviewBlockUiState>> =
        _uiState.asStateFlow()

    private val _events = MutableSharedFlow<ReviewBlockEvent>()
    val events: SharedFlow<ReviewBlockEvent> = _events.asSharedFlow()

    private var getDataJob: Job? = null

    init {
        loadData()
    }

    fun loadData() {
        getDataJob?.cancel()

        val reviewBlockIdFlow = flow {
            emit(
                blockUseCases.getReviewBlockId()
            )
        }

        getDataJob = viewModelScope.launch {
            combine(
                blockUseCases.getReviewBlockWords(),
                wordUseCases.getWordsWithReviewIndicator(),
                reviewBlockIdFlow
            ) { reviewBlockData, allWords, reviewBlockId ->
                ReviewBlockUiState(
                    reviewWords = reviewBlockData.words.map { it.toUiModel() },
                    allWords = allWords.map { it.toUiModel() },
                    reviewBlockId = reviewBlockId
                )
            }.map { data ->
                ResultUiState.Success(data) as ResultUiState<ReviewBlockUiState>
            }.onStart {
                emit(ResultUiState.Loading)
                // small UX delay to prevent loading flashing
                delay(200.milliseconds)
            }.catch { e ->
                Log.e(TAG, "An error occurred: ${e.message}")
                emit(ResultUiState.Error(e))
            }.collect {
                _uiState.value = it
            }
        }
    }

    fun closeScreen() {
        viewModelScope.launch {
            _events.emit(ReviewBlockEvent.CloseScreen)
        }
    }

    fun addWordToReviewBlock(wordId: Int) {
        viewModelScope.launch {
            blockUseCases.addWordToReviewBlock(wordId)
        }
    }

    fun removeWordFromReviewBlock(wordId: Int) {
        viewModelScope.launch {
            blockUseCases.removeWordFromReviewBlock(wordId)
        }
    }
}