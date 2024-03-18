package com.picpay.desafio.android.data

import com.picpay.desafio.android.data.local.dao.UserDao
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.util.SUCCESS
import kotlinx.coroutines.coroutineScope

class UserRepository(
    private val remoteDataSource: UserDataSource,
    private val localDataSource: UserDataSource,
    private val userDao: UserDao
) {
    suspend fun getUsers(): Pair<List<User>, String> {
        var response = remoteDataSource.getUsers()

        if (response.second != SUCCESS)
            response = localDataSource.getUsers()
        else {
            if (response.first.isNotEmpty()) {
                deleteUsers()
                setUsers(response.first)
            }
        }

        return response
    }

    private suspend fun deleteUsers() {
        coroutineScope {
            userDao.deleteUsers()
        }
    }

    private suspend fun setUsers(users: List<User>) {
        coroutineScope {
            userDao.insertAll(*users.toTypedArray())
        }
    }
}