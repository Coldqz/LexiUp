package com.coldzz.lexiup.features.words.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldzz.lexiup.core.common.ResultUiState
import com.coldzz.lexiup.features.blocks.domain.use_case.block_use_cases.WordBlockUseCases
import com.coldzz.lexiup.features.words.domain.use_case.WordUseCases
import com.coldzz.lexiup.features.words.presentation.WordItemUiModel
import com.coldzz.lexiup.features.words.presentation.WordListEvent
import com.coldzz.lexiup.features.words.presentation.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordsListViewModel @Inject constructor(
    private val wordUseCases: WordUseCases,
    private val blockUseCases: WordBlockUseCases
) : ViewModel() {
    private val _wordsList: MutableStateFlow<ResultUiState<List<WordItemUiModel>>> =
        MutableStateFlow(
            ResultUiState.Loading
        )
    val wordsList: StateFlow<ResultUiState<List<WordItemUiModel>>> = _wordsList.asStateFlow()

    private val _events = MutableSharedFlow<WordListEvent>()
    val events: SharedFlow<WordListEvent> = _events.asSharedFlow()

    private var getDataJob: Job? = null

    init {
        loadData()
    }

    fun loadData() {
        getDataJob?.cancel()

        getDataJob = viewModelScope.launch {
            wordUseCases.getWordsWithReviewIndicator()
                .map { data ->
                    ResultUiState.Success(data.map { it.toUiModel() }) as ResultUiState<List<WordItemUiModel>>
                }
                .onStart {
                    emit(ResultUiState.Loading)
                }
                .catch { e ->
                    Log.e(TAG, "An error occurred: ${e.message}")
                    emit(ResultUiState.Error(e))
                }
                .collect {
                    _wordsList.value = it
                }
        }
    }

    fun closeScreen() {
        viewModelScope.launch {
            _events.emit(WordListEvent.CloseScreen)
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

    companion object {
        const val TAG = "WordsViewModel"
    }
}