package com.twobard.stackoverflowviewer.ui.viewmodel

import com.twobard.stackoverflowviewer.domain.network.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UsersListViewModel @Inject constructor(getUsersUseCase: GetUsersUseCase) {

    init {


    }
}