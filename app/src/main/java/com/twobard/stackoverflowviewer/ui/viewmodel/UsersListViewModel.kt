package com.twobard.stackoverflowviewer.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twobard.stackoverflowviewer.data.repository.UserRepositoryImpl
import com.twobard.stackoverflowviewer.domain.network.GetUsersUseCase
import com.twobard.stackoverflowviewer.domain.user.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UsersListViewModel @Inject constructor(val getUsersUseCase: GetUsersUseCase) : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    private val _errors = MutableSharedFlow<UserRepositoryImpl.NetworkError?>()
    val errors: SharedFlow<UserRepositoryImpl.NetworkError?> = _errors

    fun getUsers() {
        viewModelScope.launch {
            isLoading()
            val result = withContext(Dispatchers.IO) {
                getUsersUseCase.invoke()
            }

            if (result.isSuccess) {
                _users.value = result.getOrNull() ?: emptyList()
            } else if (result.isFailure) {
                handleError(result.exceptionOrNull() as UserRepositoryImpl.NetworkError)
            }

            doneLoading()
        }
    }

    fun isLoading() {

    }

    fun doneLoading() {

    }

    suspend fun handleError(e: UserRepositoryImpl.NetworkError?) {
        e?.let {
            log(e)
            _errors.emit(e)
        } ?: run {
            //Something went very wrong
            log("Illegal state")
            _errors.emit(UserRepositoryImpl.NetworkError.UnknownError())
        }
    }

    fun log(str: String){
        //Log to firebase etc
    }

    fun log(e: UserRepositoryImpl.NetworkError){
        //Log to firebase etc
    }

    init {


    }
}