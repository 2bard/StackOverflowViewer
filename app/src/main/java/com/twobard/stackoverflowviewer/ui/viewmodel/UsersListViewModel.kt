package com.twobard.stackoverflowviewer.ui.viewmodel

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twobard.stackoverflowviewer.data.repository.UserRepositoryImpl
import com.twobard.stackoverflowviewer.domain.events.FollowedEvent
import com.twobard.stackoverflowviewer.domain.network.GetFollowsUseCase
import com.twobard.stackoverflowviewer.domain.network.GetUsersUseCase
import com.twobard.stackoverflowviewer.domain.state.UsersListSerializer
import com.twobard.stackoverflowviewer.domain.user.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.parcelize.Parcelize
import javax.inject.Inject


@HiltViewModel
class UsersListViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    val usersListSerializer: UsersListSerializer,
    val getUsersUseCase: GetUsersUseCase,
    val getFollowsUseCase: GetFollowsUseCase
) : ViewModel() {

    //SavedState keys
    companion object {
        private const val USERS_KEY = "users"
        private val FOLLOWS_KEY = "follows"
        private val LOADING_KEY = "is_loading"
    }


    //UI State

    //List loading state
    private val _isLoading = MutableStateFlow<Boolean>(
        savedStateHandle.get<Boolean>(LOADING_KEY) ?: false
    )
    val loading: StateFlow<Boolean> = _isLoading

    //List error state
    private val _errors = MutableSharedFlow<UserRepositoryImpl.NetworkError?>(replay = 1) //replay needed for testing - collect is called after first emission
    val errors: SharedFlow<UserRepositoryImpl.NetworkError?> = _errors

    //Followed event
    private val _followedEvent = MutableSharedFlow<FollowedEvent>()
    val followedEvent: SharedFlow<FollowedEvent> = _followedEvent

    //SavedState-backed list of users
    private val _users = MutableStateFlow<List<User>>(
        savedStateHandle.get<String>(USERS_KEY)
            ?.let {
                usersListSerializer.deserialize(it)
            }
            ?: emptyList<User>()
    )
    val users: StateFlow<List<User>> = _users

    //SavedState-backed list of follows
    private val _follows = MutableStateFlow<List<Int>>(
        savedStateHandle.get<String>(FOLLOWS_KEY)
            ?.let { usersListSerializer.deserializeFollows(it) }
            ?: emptyList()
    )
    val follows: StateFlow<List<Int>> = _follows


    fun setFollows(follows: List<Int>) {
        _follows.value = follows
        //add them to saved state so they survive process death
        savedStateHandle[FOLLOWS_KEY] = usersListSerializer.serializeFollows(follows)
    }

    fun setUsers(users: List<User>) {
        _users.value = users
        //add them to saved state so they survive process death
        savedStateHandle[USERS_KEY] = usersListSerializer.serialize(users)
    }

    init {
        if (_users.value.isEmpty()) {
            getUsers()
        }
        if (_follows.value.isEmpty()) {
            getFollows()
        }
    }

    fun getUsers() {
        viewModelScope.launch {
            try {
                isLoading()
                val result = withContext(Dispatchers.IO) {
                    getUsersUseCase.invoke()
                }

                if (result.isSuccess) {
                    setUsers(result.getOrNull() ?: emptyList())
                } else if (result.isFailure) {
                    handleError(result.exceptionOrNull())
                }
            } finally {
                //In case the coroutine get cancelled
                doneLoading()
            }
        }
    }

    fun getFollows() {
        viewModelScope.launch {
            getFollowsUseCase.invoke().collect { result ->
                setFollows(result)
            }
        }
    }

    fun isLoading() {
        _isLoading.value = true
        savedStateHandle[LOADING_KEY] = true
    }

    fun doneLoading() {
        _isLoading.value = false
        savedStateHandle[LOADING_KEY] = false
    }

    suspend fun handleError(e: Throwable?) {
        e?.let {
            log(e)
            //probs should be when() for type checking
            if (e is UserRepositoryImpl.NetworkError) {
                _errors.emit(e)
            } else {
                //We don't know about this exception type, treat it as unknown
                _errors.emit(UserRepositoryImpl.NetworkError.UnknownError())
            }

        } ?: run {
            //Something went very wrong
            log("Illegal state")
            _errors.emit(UserRepositoryImpl.NetworkError.UnknownError())
        }
    }

    fun log(str: String) {
        //Log to firebase etc
    }

    fun log(e: Throwable) {
        //Log to firebase etc
    }

    fun changeFollowStatus(user: User) {
        viewModelScope.launch {
            val followedEvent = getFollowsUseCase.changeFollow(user)
            followedEvent?.let {
                _followedEvent.emit(it)
            }
        }
    }

    fun refresh() {
        getUsers()
        getFollows()
    }

    // StateFlow of List<Pair<User, Boolean>>: each user paired with whether their id exists in the follows repository
    val usersWithFollowStatus: StateFlow<List<Pair<User, Boolean>>> =
        users.combine(follows) { usersList, followsList ->
            usersList.map { user ->
                Pair(user, followsList.contains(user.id))
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )
}