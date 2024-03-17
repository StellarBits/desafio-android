package com.picpay.desafio.android.data

import com.picpay.desafio.android.domain.model.User

interface UserDataSource {
    suspend fun getUsers(): Pair<List<User>, String>
}