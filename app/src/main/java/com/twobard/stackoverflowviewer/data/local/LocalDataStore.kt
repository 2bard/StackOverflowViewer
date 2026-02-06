package com.twobard.stackoverflowviewer.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.preferencesDataStoreFile

import androidx.compose.runtime.getValue
import com.twobard.stackoverflowviewer.domain.local.LocalDataStoreInterface
import com.twobard.stackoverflowviewer.domain.user.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


//from https://developer.android.com/topic/libraries/architecture/datastore
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LocalDataStore(val context: Context) : LocalDataStoreInterface {

    private val dataStore: DataStore<Preferences> = context.dataStore
    private val USERS_KEY = stringSetPreferencesKey("user_ids")


    override fun followedUsers(): Flow<List<Int>> = dataStore.data.map { preferences ->
        //We've got to store strings because localdata doesn't support lists of strings
        preferences[USERS_KEY]?.mapNotNull { it.toIntOrNull() } ?: emptyList()
    }

    override suspend fun followUser(user: User) {
        dataStore.edit { preferences ->
            val current = preferences[USERS_KEY]?.toMutableSet() ?: mutableSetOf()
            current.add(user.id.toString())
            preferences[USERS_KEY] = current
        }
    }
}