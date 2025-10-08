package com.coldzz.lexiup.features.blocks.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldzz.lexiup.core.common.FakeDataSamples
import com.coldzz.lexiup.features.blocks.data.local.WordBlockWithOxfordWords
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
    private val _blocksList: MutableStateFlow<List<WordBlockWithOxfordWords>> = MutableStateFlow(emptyList<WordBlockWithOxfordWords>())
    val blocksList: StateFlow<List<WordBlockWithOxfordWords>> = _blocksList.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getWordBlockWithOxfordWords().collect { blocksList ->
                _blocksList.value = blocksList
                Log.d(TAG, blocksList.joinToString(";"))
            }
        }
    }

    fun addSampleData() {
        viewModelScope.launch {
            repository.addBlock(
                FakeDataSamples.fakeBlocksList[0]
            )

            repository.addWordsToBlock(
                blockId = 1,
                wordIdList = FakeDataSamples.fakeWordsList1.map { element ->
                    element.id
                }
            )
        }
    }
}