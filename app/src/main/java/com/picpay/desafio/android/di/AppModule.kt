package com.picpay.desafio.android.di

import com.picpay.desafio.android.data.UserDataSource
import com.picpay.desafio.android.data.UserRepositoryImpl
import com.picpay.desafio.android.data.local.LocalUserDataSource
import com.picpay.desafio.android.data.remote.RemoteUserDataSource
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.domain.usecase.GetUsersUseCase
import com.picpay.desafio.android.domain.usecase.GetUsersUseCaseImpl
import com.picpay.desafio.android.presentation.userlist.UserListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    factory<GetUsersUseCase> { GetUsersUseCaseImpl(get()) }

    factory<UserDataSource>(named("remote")) { RemoteUserDataSource() }

    factory<UserDataSource>(named("local")) { LocalUserDataSource(get()) }

    factory<UserRepository> { UserRepositoryImpl(get(named("remote")), get(named("local")), get()) }

    viewModel { UserListViewModel(get()) }
}