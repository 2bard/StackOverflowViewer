package com.twobard.stackoverflowviewer.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twobard.stackoverflowviewer.data.repository.UserRepositoryImpl
import com.twobard.stackoverflowviewer.domain.network.GetFollowsUseCase
import com.twobard.stackoverflowviewer.domain.network.GetUsersUseCase
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
import javax.inject.Inject

@HiltViewModel
class UsersListViewModel @Inject constructor(
    val getUsersUseCase: GetUsersUseCase,
    val getFollowsUseCase: GetFollowsUseCase) : ViewModel() {

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val loading: StateFlow<Boolean> = _isLoading

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    private val _errors = MutableSharedFlow<UserRepositoryImpl.NetworkError?>()
    val errors: SharedFlow<UserRepositoryImpl.NetworkError?> = _errors

    private val _follows = MutableStateFlow<List<Int>>(emptyList())
    val follows: StateFlow<List<Int>> = _follows

    init {
        getUsers()
        getFollows()
    }

    fun getFollows() {
        viewModelScope.launch {
            getFollowsUseCase.invoke().collect { result ->
                _follows.value = result
            }
        }
    }

    fun getUsers() {
        viewModelScope.launch {
            isLoading()
            val result = withContext(Dispatchers.IO) {
                getUsersUseCase.invoke()
            }

            if (result.isSuccess) {
                _users.value = result.getOrNull() ?: emptyList()
            } else if (result.isFailure) {
                handleError(result.exceptionOrNull())
            }

            doneLoading()
        }
    }

    fun isLoading() {
        _isLoading.value = true
    }

    fun doneLoading() {
        _isLoading.value = false
    }

    suspend fun handleError(e: Throwable?) {
        e?.let {
            log(e)
            //probs should be when() for type checking
            if(e is UserRepositoryImpl.NetworkError){
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

    fun log(str: String){
        //Log to firebase etc
    }

    fun log(e: Throwable){
        //Log to firebase etc
    }

    fun changeFollowStatus(user: User) {
        viewModelScope.launch {
            getFollowsUseCase.changeFollow(user)
        }
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