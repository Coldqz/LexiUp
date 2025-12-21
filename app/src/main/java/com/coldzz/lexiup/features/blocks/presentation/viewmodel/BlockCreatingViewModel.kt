package com.coldzz.lexiup.features.blocks.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.coldzz.lexiup.core.common.FakeDataSamples
import com.coldzz.lexiup.features.words.data.local.entities.WordsWithReviewBlockIndicator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BlockCreatingViewModel @Inject constructor() : ViewModel() {
    private val _blockCreatingUiState: MutableStateFlow<BlockCreatingUiState> =
        MutableStateFlow(BlockCreatingUiState())
    val blockCreatingUiState: StateFlow<BlockCreatingUiState> = _blockCreatingUiState.asStateFlow()

    init {
        // TODO: fake data for testing
        _blockCreatingUiState.value = BlockCreatingUiState(
            wordsList = FakeDataSamples.getMappedList(),
            checkedList = setOf(5221, 3815, 5882)
        )
    }

    fun onSelectedChange(wordId: Int) {
        _blockCreatingUiState.update { oldUiState ->
            val newCheckedList = if (!oldUiState.checkedList.contains(wordId)) {
                oldUiState.checkedList + wordId
            } else oldUiState.checkedList - wordId
            oldUiState.copy(checkedList = newCheckedList)
        }
    }
}

data class BlockCreatingUiState(
    val wordsList: List<WordsWithReviewBlockIndicator> = emptyList(),
    val checkedList: Set<Int> = emptySet()
)