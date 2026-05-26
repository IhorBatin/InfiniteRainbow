package com.example.infiniterainbow.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoritesDataSource(private val dataStore: DataStore<Preferences>) {

    private val favoritesKey = stringSetPreferencesKey("favorite_colors")

    fun getFavoriteColors(): Flow<Set<Int>> {
        return dataStore.data.map { preferences ->
            preferences[favoritesKey]?.map { it.toInt() }?.toSet() ?: emptySet()
        }
    }

    suspend fun toggleFavoriteColor(color: Int) {
        dataStore.edit { preferences ->
            val currentFavorites = preferences[favoritesKey]?.toMutableSet() ?: mutableSetOf()
            val colorString = color.toString()
            if (currentFavorites.contains(colorString)) {
                currentFavorites.remove(colorString)
            } else {
                currentFavorites.add(colorString)
            }
            preferences[favoritesKey] = currentFavorites
        }
    }

    suspend fun clearAllFavorites() {
        dataStore.edit { preferences ->
            preferences.remove(favoritesKey)
        }
    }
}
