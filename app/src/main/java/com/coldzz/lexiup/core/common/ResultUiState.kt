package com.coldzz.lexiup.core.common

sealed class ResultUiState<out T> {
    data object Loading : ResultUiState<Nothing>()
    data class Success<T>(val data: T) : ResultUiState<T>()
    data class Error(val throwable: Throwable) : ResultUiState<Nothing>()
}