package com.geekdroid.my_todo_ai.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.geekdroid.my_todo_ai.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferenceRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PreferenceRepository {

    private object PreferencesKeys {
        val IS_GRID_VIEW = booleanPreferencesKey("is_grid_view")
    }

    override val isGridView: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.IS_GRID_VIEW] ?: false
    }

    override suspend fun toggleViewMode() {
        dataStore.edit { preferences ->
            val current = preferences[PreferencesKeys.IS_GRID_VIEW] ?: false
            preferences[PreferencesKeys.IS_GRID_VIEW] = !current
        }
    }
}
