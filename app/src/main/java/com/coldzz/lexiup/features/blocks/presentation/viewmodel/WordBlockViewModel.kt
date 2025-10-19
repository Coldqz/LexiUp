package com.coldzz.lexiup.features.blocks.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldzz.lexiup.core.common.FakeDataSamples
import com.coldzz.lexiup.features.blocks.domain.FormattedWordBlocksList
import com.coldzz.lexiup.features.blocks.domain.WordBlockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "WordBlockViewModel"

@HiltViewModel
class WordBlockViewModel @Inject constructor(private val repository: WordBlockRepository): ViewModel() {

    private val _blocksList: MutableStateFlow<FormattedWordBlocksList> = MutableStateFlow(FormattedWordBlocksList())
    val blocksList: StateFlow<FormattedWordBlocksList> = _blocksList.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllBlocks().collect { blockList ->
                _blocksList.value = FormattedWordBlocksList.formattedList(blockList)
            }
        }
    }
    // TODO: delete this, its only for debug
    fun addSampleData() {
        viewModelScope.launch {
            /*FakeDataSamples.fakeBlocksList.forEach { element ->
                repository.addBlock(element)
            }*/

            /*repository.addBlock(
                FakeDataSamples.fakeBlocksList[0]
            )*/

            /*repository.addWordsToBlock(
                blockId = 1,
                wordIdList = FakeDataSamples.fakeWordsList1.map { element ->
                    element.id
                }
            )*/
        }
    }
    // TODO: delete this, its only for debug
    private fun addSingleBlock() {
        viewModelScope.launch{
            repository.addBlock(FakeDataSamples.fakeBlocksList[1])
        }
    }
    // TODO: delete this, its only for debug
    private fun addSingleWordToBlock() {
        viewModelScope.launch {
            repository.addWordsToBlock(2, listOf(5632))
        }
    }
}