package com.twobard.stackoverflowviewer.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.twobard.stackoverflowviewer.data.repository.UserRepositoryImpl
import com.twobard.stackoverflowviewer.domain.network.GetFollowsUseCase
import com.twobard.stackoverflowviewer.domain.network.GetUsersUseCase
import com.twobard.stackoverflowviewer.domain.state.UsersListSerializer
import com.twobard.stackoverflowviewer.domain.user.User
import com.twobard.stackoverflowviewer.ui.viewmodel.UsersListViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.invoke
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE,
    sdk = [33])
class UsersListViewModelTest {

    private lateinit var useCase: GetUsersUseCase
    private lateinit var followsUseCase: GetFollowsUseCase
    private lateinit var viewModel: UsersListViewModel
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var usersListSerializer: UsersListSerializer

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())

        useCase = mockk<GetUsersUseCase>()
        followsUseCase = mockk<GetFollowsUseCase>()
        savedStateHandle = SavedStateHandle()
        usersListSerializer = mockk<UsersListSerializer>()

        every { usersListSerializer.serialize(any()) } returns ""
        every { usersListSerializer.serializeFollows(any()) } returns ""
    }

    //Sets up viewmodel for testing, so we don't have to repeat it in every test..
    fun setupViewModel(getUsersResult: Result<List<User>>, getFollowsResult: List<Int>){


        coEvery { useCase.invoke() } returns getUsersResult
        coEvery { followsUseCase.invoke() } returns flowOf(getFollowsResult)

        viewModel = UsersListViewModel(
            savedStateHandle = savedStateHandle,
            usersListSerializer = usersListSerializer,
            getUsersUseCase = useCase,
            getFollowsUseCase = followsUseCase
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `given an empty UserListViewModel when init() calls getUsers successfully then set State`() = runTest {

        val users = listOf(User(1, "some_user", 213, "werw"), User(2, "some_user_2", 123, "sfsd"))
        setupViewModel(
            getUsersResult = Result.success(users),
            getFollowsResult = emptyList()
        )
        advanceUntilIdle()

        val emitted = viewModel.users.first()

        assertNotNull(emitted)
        assertEquals(2, emitted.size)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `given an empty UserListViewModel when init calls getFollows successfully then set State`() = runTest {

        val follows = listOf(1, 2, 3)
        setupViewModel(
            getUsersResult = Result.success(listOf()),
            getFollowsResult = follows
        )
        advanceUntilIdle()

        val emitted = viewModel.follows.first()

        assertNotNull(emitted)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `given an empty UserListViewModel when init returns NetworkFailure then emit error`() = runTest {

        val error = UserRepositoryImpl.NetworkError.NetworkFailure()
        setupViewModel(
            getUsersResult = Result.failure(error),
            getFollowsResult = listOf()
        )
        advanceUntilIdle()

        val emittedError = viewModel.errors.first()

        assertEquals(error, emittedError)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `given an empty UserListViewModel when init calls getUsers then set loading state`() = runTest {
         setupViewModel(
            getUsersResult = Result.success(listOf()),
            getFollowsResult = emptyList()
        )

        val emissions = mutableListOf<Boolean>()
        val job = launch {
            viewModel.loading.take(2).toList(emissions)
        }
        advanceUntilIdle()
        job.cancel()

        assertEquals(true, emissions[0])   // first event = loading started
        assertEquals(false, emissions[1])  // second event = loading finished
    }

    //Other test ideas:
    //given SavedStateHandle contains users when init then set state
    //given SavedStateHandle contains follows when init then set state

}