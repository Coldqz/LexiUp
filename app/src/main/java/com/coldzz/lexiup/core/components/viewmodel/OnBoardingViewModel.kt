package com.coldzz.lexiup.core.components.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coldzz.lexiup.core.data.datastore.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {
    fun setOnBoardedTrue() {
        viewModelScope.launch {
            dataStoreManager.setOnBoarded(true)
        }
    }
}