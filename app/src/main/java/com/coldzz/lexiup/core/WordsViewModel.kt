package com.coldzz.lexiup.core

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldzz.lexiup.core.domain.repository.WordRepository
import com.coldzz.lexiup.features.words.data.local.LevelCerf
import com.coldzz.lexiup.features.words.data.local.OxfordWords
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordsViewModel @Inject constructor(private val repository: WordRepository) : ViewModel() {

    fun testWork() {
        Log.d(TAG, repository.toString())
        viewModelScope.launch {
            repository.addWord(
                OxfordWords(
                    word = "test",
                    level = LevelCerf.A1
                )
            )
        }
    }

    companion object {
        const val TAG = "WordsViewModel"
    }
}