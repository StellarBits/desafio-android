package com.picpay.desafio.android.presentation.userlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.usecase.GetUsersUseCase
import kotlinx.coroutines.launch


class UserListViewModel(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {
    private val _usersList = MutableLiveData<Pair<List<User>, String>>()
    val usersList: LiveData<Pair<List<User>, String>> get() = _usersList

    fun getUsers() {
        viewModelScope.launch {
            _usersList.value = getUsersUseCase()
        }
    }
}