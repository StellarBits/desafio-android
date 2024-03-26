package com.picpay.desafio.android.data

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.data.local.dao.UserDao
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.factory.UserFactory
import com.picpay.desafio.android.util.SUCCESS
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class UserRepositoryImplTest {

    private val remoteDataSource = mock<UserDataSource>()
    private val localUserDataSource = mock<UserDataSource>()
    private val userDao = mock<UserDao>()
    private val userRepository = UserRepositoryImpl(remoteDataSource, localUserDataSource, userDao)

    @Test
    fun getUsers_from_remote_return_list() {
        runTest {
            // Given | Arrange
            val expectedUsers = Pair(UserFactory.users, SUCCESS)

            whenever(remoteDataSource.getUsers()).thenReturn(expectedUsers)

            // When | Act
            val actualResponse = userRepository.getUsers()

            // Then | Assert
            Assert.assertEquals(expectedUsers, actualResponse)
        }
    }

    @Test
    fun getUsers_from_local_return_list() {
        runTest {
            // Given | Arrange
            val expectedUsers = Pair(UserFactory.users, SUCCESS)

            whenever(remoteDataSource.getUsers()).thenReturn(Pair(listOf(), ""))
            whenever(localUserDataSource.getUsers()).thenReturn(expectedUsers)

            // When | Act
            val actualResponse = userRepository.getUsers()

            // Then | Assert
            Assert.assertEquals(expectedUsers, actualResponse)
        }
    }
}