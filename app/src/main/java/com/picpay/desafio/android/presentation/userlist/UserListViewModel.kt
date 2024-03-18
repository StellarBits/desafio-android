package com.picpay.desafio.android.presentation.userlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.usecase.GetUsersUseCase
import com.picpay.desafio.android.util.NO_ERROR
import com.picpay.desafio.android.util.SUCCESS
import kotlinx.coroutines.launch


class UserListViewModel(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {
    private val _usersList = MutableLiveData<List<User>>()
    val usersList: LiveData<List<User>> get() = _usersList

    private val _getUsersListError = MutableLiveData(NO_ERROR)
    val getUsersListError: LiveData<String> get() = _getUsersListError

    fun getUsers() {
        viewModelScope.launch {
            val usersResponse = getUsersUseCase()

            when (usersResponse.second) {
                SUCCESS -> _usersList.postValue(usersResponse.first)
                else -> _getUsersListError.postValue(usersResponse.second)
            }
        }
    }
}