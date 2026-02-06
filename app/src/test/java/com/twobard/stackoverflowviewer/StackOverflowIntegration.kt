package com.twobard.stackoverflowviewer

import com.twobard.stackoverflowviewer.data.api.StackOverflowApiImpl
import kotlinx.coroutines.test.runTest


import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.test.assertTrue

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE,
    sdk = [33])
class StackOverflowIntegration {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val api = retrofit.create(StackOverflowApiImpl::class.java)

    @Test
    fun `given the StackOverflow api when getUsers called then 20 users returned`() = runTest {
        val result = api.getUsers(
            page = 1,
            pageSize = 20
        )
        assertTrue(result.items.isNotEmpty())
        assertTrue(result.items.size == 20)
    }
}