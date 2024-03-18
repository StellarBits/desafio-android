package com.picpay.desafio.android.data.local

import com.picpay.desafio.android.data.UserDataSource
import com.picpay.desafio.android.data.local.dao.UserDao
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.util.NO_DATA_IN_DATABASE_ERROR
import com.picpay.desafio.android.util.SUCCESS
import com.picpay.desafio.android.util.UNKNOWN_ERROR
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.coroutines.suspendCoroutine

class LocalUserDataSource(private val userDao: UserDao) : UserDataSource {
    @OptIn(DelicateCoroutinesApi::class)
    override suspend fun getUsers(): Pair<List<User>, String> {

        return suspendCoroutine { continuation ->
            GlobalScope.launch {
                try {
                    userDao.getUsers().collect { users ->
                        if (users.isEmpty()) {
                            continuation.resumeWith(Result.success(Pair(listOf(), NO_DATA_IN_DATABASE_ERROR)))
                        } else {
                            continuation.resumeWith(Result.success(Pair(users, SUCCESS)))
                        }
                    }
                } catch (e: Exception) {
                    if (continuation.context.isActive) {
                        continuation.resumeWith(Result.success(Pair(listOf(), e.message ?: UNKNOWN_ERROR)))
                    }
                }
            }
        }
    }
}