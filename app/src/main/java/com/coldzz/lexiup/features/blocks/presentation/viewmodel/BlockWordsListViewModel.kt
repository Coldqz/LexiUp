package com.coldzz.lexiup.features.blocks.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.coldzz.lexiup.core.common.ResultUiState
import com.coldzz.lexiup.core.navigation.NavRoutes
import com.coldzz.lexiup.features.blocks.domain.use_case.block_use_cases.WordBlockUseCases
import com.coldzz.lexiup.features.blocks.presentation.BlockWordsListEvents
import com.coldzz.lexiup.features.words.presentation.BlockWordsUiState
import com.coldzz.lexiup.features.words.presentation.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

private const val TAG = "BlockWordsListViewModel"

@HiltViewModel
class BlockWordsListViewModel @Inject constructor(
    private val useCases: WordBlockUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _uiState: MutableStateFlow<ResultUiState<BlockWordsUiState>> =
        MutableStateFlow(ResultUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<BlockWordsListEvents>()
    val events = _events.asSharedFlow()


    private var getBlocksJob: Job? = null

    init {
        loadData()
    }

    fun loadData() {
        getBlocksJob?.cancel()

        val blockId = savedStateHandle.toRoute<NavRoutes.BlockWordsList>().blockId

        getBlocksJob =
            useCases.getWordsFromBlock(blockId)
                .map { data ->
                    val uiModel = BlockWordsUiState(
                        blockNumber = data.block.blockNumber,
                        words = data.words.map { it.toUiModel() }
                    )
                    ResultUiState.Success(uiModel) as ResultUiState<BlockWordsUiState>
                }
                .onStart {
                    emit(ResultUiState.Loading)
                    // small UX delay to prevent loading flashing
                    delay(200.milliseconds)
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
            _events.emit(BlockWordsListEvents.CloseScreen)
        }
    }
}