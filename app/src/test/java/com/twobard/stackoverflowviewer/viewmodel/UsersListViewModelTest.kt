package com.twobard.stackoverflowviewer.viewmodel

import com.twobard.stackoverflowviewer.data.repository.UserRepositoryImpl
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
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE,
    sdk = [33])
class UsersListViewModelTest {

    private lateinit var useCase: GetUsersUseCase
    private lateinit var viewModel: UsersListViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        useCase = mockk<GetUsersUseCase>()
        viewModel = UsersListViewModel(useCase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `given a UserListViewModel when getUsers then set State`() = runTest {

        val users = listOf(User(1, "some_user"), User(2, "some_user_2"))

        coEvery { useCase.invoke() } returns Result.success(users)

        viewModel.getUsers()
        advanceUntilIdle()

        val emitted = viewModel.users.first()

        assertNotNull(emitted)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `given a UserListViewModel when getUsers returns NetworkFailure then emit error`() = runTest {

        val error = UserRepositoryImpl.NetworkError.NetworkFailure()
        coEvery { useCase.invoke() } returns Result.failure(error)

        viewModel.getUsers()
        advanceUntilIdle()

        val emittedError = viewModel.errors.first()

        assertEquals(error, emittedError)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `given a UserListViewModel when getUsers returns NoInternet then emit error`() = runTest {

        val error = UserRepositoryImpl.NetworkError.NoInternet()
        coEvery { useCase.invoke() } returns Result.failure(error)

        viewModel.getUsers()
        advanceUntilIdle()

        val emittedError = viewModel.errors.first()

        assertEquals(error, emittedError)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `given a UserListViewModel when getUsers returns UnknownError then emit error`() = runTest {

        val error = UserRepositoryImpl.NetworkError.UnknownError()
        coEvery { useCase.invoke() } returns Result.failure(error)

        viewModel.getUsers()
        advanceUntilIdle()

        val emittedError = viewModel.errors.first()

        assertEquals(error, emittedError)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `given a UserListViewModel when getUsers returns generic Exception then emit Unknown error`() = runTest {

        val error = Exception("whoops")
        coEvery { useCase.invoke() } returns Result.failure(error)

        viewModel.getUsers()
        advanceUntilIdle()

        val emittedError = viewModel.errors.first()

        assert(emittedError is UserRepositoryImpl.NetworkError.UnknownError)
    }



}