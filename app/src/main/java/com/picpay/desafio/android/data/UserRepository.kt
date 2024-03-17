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
        return if (remoteDataSource.getUsers().second != SUCCESS)
            localDataSource.getUsers()
        else {
            val users = remoteDataSource.getUsers()

            if (users.first.isNotEmpty()) {
                deleteUsers()
                setUsers(users.first)
            }

            users
        }
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