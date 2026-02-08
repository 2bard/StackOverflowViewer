package com.twobard.stackoverflowviewer.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.twobard.stackoverflowviewer.domain.events.FollowedEvent

import com.twobard.stackoverflowviewer.domain.local.LocalDataStoreInterface
import com.twobard.stackoverflowviewer.domain.user.User
import com.twobard.stackoverflowviewer.ui.viewmodel.UsersListViewModel
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

    override suspend fun changeFollowStatus(user: User) : FollowedEvent? {

        var followedEvent: FollowedEvent? = null
        dataStore.edit { preferences ->
            val current = preferences[USERS_KEY]?.toMutableSet() ?: mutableSetOf()


            if(current.contains(user.id.toString())){
                current.remove(user.id.toString())
                followedEvent = FollowedEvent.Unfollowed(user = user)
            } else {
                current.add(user.id.toString())
                followedEvent = FollowedEvent.Followed(user = user)
            }

            preferences[USERS_KEY] = current
        }

        return followedEvent
    }
}