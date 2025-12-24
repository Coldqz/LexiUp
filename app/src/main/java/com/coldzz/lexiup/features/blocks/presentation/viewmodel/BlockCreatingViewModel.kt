package com.coldzz.lexiup.features.blocks.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldzz.lexiup.features.blocks.domain.WordBlockRepository
import com.coldzz.lexiup.features.words.domain.repository.WordRepository
import com.coldzz.lexiup.features.words.presentation.WordItemUiModel
import com.coldzz.lexiup.features.words.presentation.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

private const val TAG = "BlockCreatingViewModel"

@HiltViewModel
class BlockCreatingViewModel @Inject constructor(
    private val blocksRepository: WordBlockRepository,
    private val wordsRepository: WordRepository
) : ViewModel() {
    private val _blockCreatingUiState: MutableStateFlow<BlockCreatingUiState> =
        MutableStateFlow(BlockCreatingUiState())
    val blockCreatingUiState: StateFlow<BlockCreatingUiState> = _blockCreatingUiState.asStateFlow()

    init {
        viewModelScope.launch {
            loadRandomWordsToFlow(5)
        }
    }

    // Change state of the check icon
    fun onSelectedChange(wordId: Int) {
        _blockCreatingUiState.update { state ->
            val newCheckedList = if (!state.checkedList.contains(wordId)) {
                state.checkedList + wordId
            } else state.checkedList - wordId
            state.copy(checkedList = newCheckedList)
        }
    }

    /*fun actionOnCreateButton() {
        viewModelScope.launch {
            val checkedList = _blockCreatingUiState.value.checkedList
            blocksRepository.run {
                addBlock(
                    wordBlock = WordBlock(
                        learningLevel = LearningLevelIndicator.One,
                        blockType = BlockTypes.PLANNED
                    )
                )
                addWordsToBlock()
            }
        }
    }*/

    // add 3 more words to the list
    fun suggestWords() = loadRandomWordsToFlow(randomWordsCount = 3)

    /**
     * Fetch unique random words from the database and append them to the end of the flow's wordsList.
     * [randomWordsCount] to adjust words count
     * */
    private fun loadRandomWordsToFlow(randomWordsCount: Int) {
        viewModelScope.launch {
            val wordIdList = mutableListOf<Int>()
            val totalWordsCount = wordsRepository.getCachedWordsCount()
            while (wordIdList.size < randomWordsCount) {
                wordIdList.add(Random.nextInt(until = totalWordsCount))
            }
            val resultList = wordsRepository.getWords(wordIdList).toMutableList()
            _blockCreatingUiState.update { state ->
                state.copy(
                    wordsList = state.wordsList + resultList.map { it.toUiModel() }
                )
            }
        }
    }
}

data class BlockCreatingUiState(
    val wordsList: List<WordItemUiModel> = emptyList(),
    val checkedList: Set<Int> = emptySet()
)