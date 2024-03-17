package com.picpay.desafio.android.domain.usecase

import com.picpay.desafio.android.data.UserRepository
import com.picpay.desafio.android.domain.model.User

class GetUsersUseCaseImpl(
    private val userRepository: UserRepository
) : GetUsersUseCase {

    override suspend fun invoke(): Pair<List<User>, String> {
        return userRepository.getUsers()
    }
}