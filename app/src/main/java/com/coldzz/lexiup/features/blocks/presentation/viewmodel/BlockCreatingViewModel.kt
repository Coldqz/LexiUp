package com.coldzz.lexiup.features.blocks.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldzz.lexiup.core.common.Constants
import com.coldzz.lexiup.core.common.ResultUiState
import com.coldzz.lexiup.features.blocks.domain.CreateBlockResult
import com.coldzz.lexiup.features.blocks.domain.use_case.block_use_cases.WordBlockUseCases
import com.coldzz.lexiup.features.blocks.presentation.BlockCreatingEvent
import com.coldzz.lexiup.features.blocks.presentation.BlockCreatingUiState
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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

private const val TAG = "BlockCreatingViewModel"

@HiltViewModel
class BlockCreatingViewModel @Inject constructor(
    private val blockUseCases: WordBlockUseCases, private val wordUseCases: WordUseCases
) : ViewModel() {
    private val _blockCreatingUiState: MutableStateFlow<ResultUiState<BlockCreatingUiState>> =
        MutableStateFlow(ResultUiState.Loading)
    val blockCreatingUiState: StateFlow<ResultUiState<BlockCreatingUiState>> =
        _blockCreatingUiState.asStateFlow()

    private val _events: MutableSharedFlow<BlockCreatingEvent> = MutableSharedFlow()
    val events: SharedFlow<BlockCreatingEvent> = _events.asSharedFlow()

    private var getDataJob: Job? = null

    init {
        loadData()
    }

    // Change state of the check icon
    fun onSelectedChange(wordId: Int) {
        _blockCreatingUiState.update { oldResultState ->
            // here we check if data is Success and if so we have smart cast to Success after this line
            if (oldResultState !is ResultUiState.Success) {
                return@update oldResultState
            }

            val oldState = oldResultState.data

            val word = oldState.searchBarList.firstOrNull { it.id == wordId }
                ?: return@update oldResultState

            val wordIsChecked = oldState.checkedList.contains(wordId)

            val wordIsInMainList = oldState.wordsList.contains(word)
            oldResultState.copy(
                data = oldState.copy(
                    wordsList = if (!wordIsInMainList) {
                        // if word is in the search suggestions then add to the main list
                        oldState.wordsList + word
                    } else {
                        oldState.wordsList
                    },
                    checkedList = if (!wordIsChecked) {
                        oldState.checkedList + wordId
                    } else {
                        oldState.checkedList - wordId
                    }
                )
            )
        }
    }

    // function to enable and disable loading in the create blocks button
    private fun changeCreateButtonLoadingState(enabled: Boolean) {
        _blockCreatingUiState.update { oldState ->
            if (oldState is ResultUiState.Success) {
                oldState.copy(
                    data = oldState.data.copy(
                        isCreateButtonLoading = enabled
                    )
                )
            } else {
                oldState
            }
        }
    }

    fun createBlock() {
        if (_blockCreatingUiState.value is ResultUiState.Success) {
            val wordIds =
                (_blockCreatingUiState.value as ResultUiState.Success<BlockCreatingUiState>).data.checkedList.toList()

            // actions based on result of block creating
            viewModelScope.launch {
                changeCreateButtonLoadingState(true)
                // small delay for loading indicator to spin
                delay(500.milliseconds)
                val result = blockUseCases.createBlockWithWords(wordIds)

                when (result) {
                    CreateBlockResult.MinWordsNotReached -> {
                        changeCreateButtonLoadingState(false)
                        _events.emit(
                            BlockCreatingEvent.ShowToast("Block must have 10 words at least")
                        )
                    }

                    CreateBlockResult.Success -> {
                        changeCreateButtonLoadingState(false)
                        _events.emit(
                            BlockCreatingEvent.ShowToast("Block was successfully created")
                        )
                        _events.emit(BlockCreatingEvent.CloseScreen)
                    }
                }
            }
        }
    }

    /**
     * Fetch unique random words from the database and append them to the end of the wordsList.
     * */
    fun suggestWords() {
        viewModelScope.launch {
            _blockCreatingUiState.update { oldState ->
                if (oldState is ResultUiState.Success) {
                    val randomWords = blockUseCases.loadRandomWords(
                        Constants.SUGGEST_WORDS_COUNT,
                        oldState.data.wordsList.map { it.id })
                    oldState.copy(
                        data = oldState.data.copy(
                            wordsList = oldState.data.wordsList + randomWords.map { it.toUiModel() })
                    )
                } else oldState
            }
        }
    }

    fun closeScreen() {
        viewModelScope.launch {
            _events.emit(BlockCreatingEvent.CloseScreen)
        }
    }

    /*
    * Main function to load data, combine flow to fetch all 5k words
    * and one shot query to get 10 words for the screen.
    * Have result states to display loading.
    * */
    fun loadData() {
        getDataJob?.cancel()

        val loadRandomWordsFlow = flow {
            emit(blockUseCases.loadRandomWords(Constants.MIN_WORDS_COUNT_ON_BLOCK_CREATING))
        }

        getDataJob = viewModelScope.launch {
            combine(
                wordUseCases.getWordsWithReviewIndicator(),
                loadRandomWordsFlow
            ) { allWords, wordsToPick ->
                BlockCreatingUiState(
                    wordsList = wordsToPick.map { it.toUiModel() },
                    searchBarList = allWords.map { it.toUiModel() }
                )
            }
                .map { state ->
                    ResultUiState.Success(state) as ResultUiState<BlockCreatingUiState>
                }
                .onStart {
                    emit(ResultUiState.Loading)
                    // UX delay to avoid flashing loading state
                    delay(200.milliseconds)
                }
                .catch { e ->
                    Log.e(TAG, "An error occurred: ${e.message}")
                    emit(ResultUiState.Error(throwable = e))
                }
                .collect {
                    _blockCreatingUiState.value = it
                }
        }
    }
}