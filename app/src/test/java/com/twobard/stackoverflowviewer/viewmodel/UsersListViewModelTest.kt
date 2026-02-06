package com.twobard.stackoverflowviewer.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.twobard.stackoverflowviewer.domain.network.GetUsersUseCase
import com.twobard.stackoverflowviewer.domain.user.User
import com.twobard.stackoverflowviewer.ui.viewmodel.UsersListViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.intArrayOf
import kotlin.invoke
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE,
    sdk = [33])
class UsersListViewModelTest {

    private lateinit var useCase: GetUsersUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        useCase = mockk<GetUsersUseCase>()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `given a UserListViewModel when getUsers then set State`() = runTest {

        var viewModel = UsersListViewModel(useCase)

        val users = listOf(User(1, "some_user"), User(2, "some_user_2"))

        coEvery { useCase.invoke() } returns Result.success(users)

        viewModel.getUsers()
        advanceUntilIdle()

        val emitted = viewModel.users.first()

        assertNotNull(emitted)
    }

}