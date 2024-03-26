package com.picpay.desafio.android.domain.usecase

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.factory.UserFactory
import com.picpay.desafio.android.util.SUCCESS
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class GetUsersUseCaseImplTest {

    private val userRepositoryImpl = mock<UserRepository>()
    private val getUsersUseCaseImpl = GetUsersUseCaseImpl(userRepositoryImpl)

    @Test
    fun getUsers_from_local_return_list_with_success() {
        runTest {
            // Given | Arrange
            val expectedUsers = Pair(UserFactory.users, SUCCESS)

            whenever(userRepositoryImpl.getUsers()).thenReturn(expectedUsers)

            // When | Act
            val actualResponse = getUsersUseCaseImpl()

            // Then | Assert
            Assert.assertEquals(expectedUsers, actualResponse)
        }
    }

    @Test
    fun getUsers_from_local_return_list_with_failure() {
        runTest {
            // Given | Arrange
            val expectedUsers = Pair(listOf<User>(), "")

            whenever(userRepositoryImpl.getUsers()).thenReturn(expectedUsers)

            // When | Act
            val actualResponse = getUsersUseCaseImpl()

            // Then | Assert
            Assert.assertEquals(expectedUsers.first, actualResponse.first)
        }
    }
}