package com.coldzz.lexiup.core.common

sealed class ResultDataState<out T> {
    data object Loading : ResultDataState<Nothing>()
    data class Success<T>(val data: T) : ResultDataState<T>()
    data class Error(val throwable: Throwable): ResultDataState<Nothing>()
}