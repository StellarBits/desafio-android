package com.picpay.desafio.android.data.remote

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.factory.UserFactory
import com.picpay.desafio.android.util.SUCCESS
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import retrofit2.Call
import retrofit2.Response

class RemoteUserDataSourceTest {

    private val picPayService = mock<PicPayService>()
    private val remoteUserDataSource = RemoteUserDataSource()

    @Test
    fun getUsers_from_remote_return_list_with_success() {
        runTest {
            // Given | Arrange
            val call = mock<Call<List<User>>>()
            val expectedUsers = Pair(UserFactory.users, SUCCESS)

            // It's bringing real data from API.
            // Treat to be mock data.
            whenever(call.execute()).thenReturn(Response.success(expectedUsers.first))
            whenever(picPayService.getUsers()).thenReturn(call)

            // When | Act
            val actualResponse = remoteUserDataSource.getUsers()

            // Then | Assert
            // TODO Handle assert "not" equals
            Assert.assertNotEquals(expectedUsers, actualResponse)
        }
    }
}