package com.coldzz.lexiup.core.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManagerImpl @Inject constructor(
    val dataStore: DataStore<Preferences>
) : DataStoreManager {
    private object PreferencesKeys {
        val IS_ONBOARDED = booleanPreferencesKey("is_onboarded")
    }

    override suspend fun setOnBoarded(completed: Boolean) {
        dataStore.edit {
            it[PreferencesKeys.IS_ONBOARDED] = completed
        }
    }

    override val isOnBoarded: Flow<Boolean> = dataStore.data.map {
        it[PreferencesKeys.IS_ONBOARDED] ?: false
    }
}