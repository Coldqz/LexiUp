package com.coldzz.lexiup.core.data.datastore

import kotlinx.coroutines.flow.Flow

interface DataStoreManager {
    suspend fun setOnBoarded(completed:Boolean)

    val isOnBoarded: Flow<Boolean>
}