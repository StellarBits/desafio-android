package com.picpay.desafio.android.presentation.userlist

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.usecase.GetUsersUseCase
import com.picpay.desafio.android.factory.UserFactory
import com.picpay.desafio.android.util.SUCCESS
import com.picpay.desafio.android.util.UNKNOWN_ERROR
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class UserListViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private val getUsersUseCase = mock<GetUsersUseCase>()
    private val viewModel = UserListViewModel(getUsersUseCase)
    private val usersListObserver = mock<Observer<List<User>>>()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    // Remote
    @Test
    fun getUsers_from_remote_return_list_with_success() {
        testDispatcher.run {
            runTest {
                // Given | Arrange
                val nullUser1 = mock<User>() // Usuário com campos nulos
                val nullUser2 = mock<User>() // Usuário com campos nulos
                val nullUsers = listOf(nullUser1, nullUser2) // Lista não nula

                val expectedUsers = Pair(nullUsers, SUCCESS)

                whenever(getUsersUseCase()).thenReturn(expectedUsers) // Esperado uma lista vazia como "success"

                viewModel.usersList.observeForever(usersListObserver)

                // When | Act
                viewModel.getUsers()
                testDispatcher.scheduler.advanceUntilIdle()

                // Then | Assert
                verify(usersListObserver).onChanged(expectedUsers.first)
            }
        }
    }

    @Test
    fun getUsers_from_remote_return_list_with_error() {
        testDispatcher.run {
            runTest {
                // Given | Arrange
                val expectedUsers = Pair(listOf<User>(), "")

                whenever(getUsersUseCase()).thenReturn(expectedUsers)

                viewModel.usersList.observeForever(usersListObserver)

                // When | Act
                viewModel.getUsers()
                testDispatcher.scheduler.advanceUntilIdle()

                // Then | Assert
                verify(usersListObserver, times(0)).onChanged(expectedUsers.first)
            }
        }
    }
}