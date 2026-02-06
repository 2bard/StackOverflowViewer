package com.twobard.stackoverflowviewer.local

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.test.core.app.ApplicationProvider
import com.twobard.stackoverflowviewer.data.local.LocalDataStore
import com.twobard.stackoverflowviewer.domain.user.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.File
import kotlin.intArrayOf

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE,
    sdk = [33])
class LocalDataStoreTests {
    private lateinit var dataStoreFile: File
    private lateinit var localDataStore: LocalDataStore
    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        dataStoreFile = File(context.filesDir, "test_datastore.preferences_pb")
        val testDataStore = PreferenceDataStoreFactory.create(
            produceFile = { dataStoreFile }
        )
        localDataStore = LocalDataStore(context).apply {
            // Overwrite the internal dataStore for testing
            val dataStoreField = LocalDataStore::class.java.getDeclaredField("dataStore")
            dataStoreField.isAccessible = true
            dataStoreField.set(this, testDataStore)
        }
    }

    @After
    fun tearDown() {
        dataStoreFile.delete()
    }

    @Test
    fun testFollowAndRetrieveUsers() = runBlocking {
        val user = User(123, "Test User", 123, "")

        //Follow
        localDataStore.changeFollowStatus(user)
        val followed = localDataStore.followedUsers().first()
        assertEquals(1, followed.size)
        assertEquals(listOf(123), followed)

        //Unfollow
        localDataStore.changeFollowStatus(user)
        val newFollowed = localDataStore.followedUsers().first()

        assertEquals(0, newFollowed.size)
    }
}