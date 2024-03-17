package com.picpay.desafio.android.domain.usecase

import com.picpay.desafio.android.domain.model.User

interface GetUsersUseCase {
    suspend operator fun invoke(): Pair<List<User>, String>
}