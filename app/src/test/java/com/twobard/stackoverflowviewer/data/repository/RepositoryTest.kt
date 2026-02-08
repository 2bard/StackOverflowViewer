package com.twobard.stackoverflowviewer.data.repository

import com.twobard.stackoverflowviewer.StackOverflowUserDtoBuilder
import com.twobard.stackoverflowviewer.data.api.StackOverflowApiImpl
import com.twobard.stackoverflowviewer.data.dto.StackOverflowUserDto
import com.twobard.stackoverflowviewer.data.dto.StackOverflowUsersResponse
import com.twobard.stackoverflowviewer.data.repository.UserRepositoryImpl
import com.twobard.stackoverflowviewer.domain.user.User
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException
import kotlin.intArrayOf
import kotlin.test.assertEquals

class RepositoryTest {

    private lateinit var api: StackOverflowApiImpl
    private lateinit var repository: UserRepositoryImpl

    @Before
    fun setup() {
        api = mockk<StackOverflowApiImpl>()
        repository = UserRepositoryImpl(api)
    }

    @Test
    fun `given api returns users when getUsers then correct Users returned`() = runTest {
        //the name of this test is horrible

        val mockResult = StackOverflowUsersResponse(items = listOf(
            StackOverflowUserDtoBuilder().apply {
                user_id = 1
                display_name="somename-1"
            }.build(),
            StackOverflowUserDtoBuilder().apply {
                user_id = 2
                display_name="somename-2"
            }.build()
        ), has_more = false, quota_max = 123, quota_remaining = 123)

        coEvery { api.getUsers(any<Int>(), any<Int>(), any<String>(), any<String>(), any<String>()) } returns mockResult


        val items = repository.getUsers(1, 2).getOrThrow()


        //Check the response is the correct size
        assertEquals(mockResult.items.size, items.size)

        //Check all the domain models are correct
        items.forEachIndexed { index, item ->
            assertEquals(index + 1, item.id)
            assertEquals("somename-${index + 1}", item.displayName)
        }
    }

    @Test
    fun `given api throws IOException when getUsers then Result is failure`() = runTest {
        val exception = IOException("network failed")
        coEvery { api.getUsers(any(), any(), any(), any(), any()) } throws exception

        val result = repository.getUsers(1, 2)

        assert(result.isFailure)
        assert(result.exceptionOrNull() is UserRepositoryImpl.NetworkError.NetworkFailure)
    }

    //Extra test ideas:
    //Exhaustive test for all exception types
}