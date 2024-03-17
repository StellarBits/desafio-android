package com.picpay.desafio.android.data.local

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.data.local.dao.UserDao
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.factory.UserFactory
import com.picpay.desafio.android.util.NO_DATA_IN_DATABASE_ERROR
import com.picpay.desafio.android.util.SUCCESS
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import java.lang.RuntimeException

class LocalUserDataSourceTest {

    private val userDao = mock<UserDao>()
    private val localUserDataSource = LocalUserDataSource(userDao)

    @Test
    fun getUsers_from_local_return_list_with_success() {
        runTest {
            // Given | Arrange
            val expectedUsers = Pair(UserFactory.users, SUCCESS)

            whenever(userDao.getUsers()).thenReturn(flowOf(expectedUsers.first))

            // When | Act
            val actualResponse = localUserDataSource.getUsers()

            // Then | Assert
            Assert.assertEquals(expectedUsers, actualResponse)
        }
    }

    @Test
    fun getUsers_from_local_return_empty_list() {
        runTest {
            // Given | Arrange
            val expectedUsers = Pair(listOf<User>(), NO_DATA_IN_DATABASE_ERROR)

            whenever(userDao.getUsers()).thenReturn(flowOf(expectedUsers.first))

            // When | Act
            val actualResponse = localUserDataSource.getUsers()

            // Then | Assert
            Assert.assertEquals(expectedUsers, actualResponse)
        }
    }

    @Test
    fun getUsers_from_local_return_list_with_error() {
        runTest {
            // Given | Arrange
            val expectedUsers = listOf<User>()

            whenever(userDao.getUsers()).thenThrow(RuntimeException())

            // When | Act
            val actualResponse = localUserDataSource.getUsers()

            // Then | Assert
            Assert.assertEquals(expectedUsers, actualResponse.first)
        }
    }
}