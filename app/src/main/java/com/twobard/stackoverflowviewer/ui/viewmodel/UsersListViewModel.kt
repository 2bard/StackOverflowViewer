package com.twobard.stackoverflowviewer.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twobard.stackoverflowviewer.domain.network.GetUsersUseCase
import com.twobard.stackoverflowviewer.domain.user.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UsersListViewModel @Inject constructor(val getUsersUseCase: GetUsersUseCase) : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

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

    }

    fun doneLoading() {

    }

    fun handleError(e: Throwable?) {
        //TODO:
    }

    init {


    }
}