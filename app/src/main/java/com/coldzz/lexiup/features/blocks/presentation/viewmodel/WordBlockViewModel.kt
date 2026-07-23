package com.coldzz.lexiup.features.blocks.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldzz.lexiup.core.common.ResultUiState
import com.coldzz.lexiup.features.blocks.domain.use_case.block_use_cases.WordBlockUseCases
import com.coldzz.lexiup.features.blocks.presentation.BlocksScreenUiState
import com.coldzz.lexiup.features.blocks.presentation.WordBlockEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "WordBlockViewModel"

@HiltViewModel
class WordBlockViewModel @Inject constructor(
    private val useCases: WordBlockUseCases,
) : ViewModel() {

    private val _uiState: MutableStateFlow<ResultUiState<BlocksScreenUiState>> =
        MutableStateFlow(ResultUiState.Loading)
    val uiState: StateFlow<ResultUiState<BlocksScreenUiState>> = _uiState.asStateFlow()

    private val _events: MutableSharedFlow<WordBlockEvent> = MutableSharedFlow()
    val event: SharedFlow<WordBlockEvent> = _events.asSharedFlow()

    private var getBlocksJob: Job? = null

    init {
        loadData()
    }

    fun loadData() {
        getBlocksJob?.cancel()

        getBlocksJob = useCases.getBlocksFlow()
            .map { data ->
                ResultUiState.Success(data) as ResultUiState<BlocksScreenUiState>
            }
            .onStart {
                emit(ResultUiState.Loading)
            }
            .catch { e ->
                Log.e(TAG, "An error occurred: ${e.message}")
                emit(ResultUiState.Error(e))
            }
            .onEach {
                _uiState.value = it
            }
            .launchIn(viewModelScope)
    }

    fun closeScreen() {
        viewModelScope.launch {
            _events.emit(WordBlockEvent.CloseScreen)
        }
    }

    fun deleteBlock(blockId: Int) {
        viewModelScope.launch {
            useCases.deleteBlock(blockId)
        }
    }

    fun activateBlock(blockId: Int) {
        viewModelScope.launch {
            useCases.activateBlock(blockId).onFailure {
                _events.emit(WordBlockEvent.ShowToast("Max active blocks count was exceeded"))
            }
        }
    }

    fun deactivateBlock(blockId: Int) {
        viewModelScope.launch {
            useCases.deactivateBlock(blockId)
        }
    }
}