package com.twobard.stackoverflowviewer.repository

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
    fun `given UserRepo when getUsers then correct Users returned`() = runTest {


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
}