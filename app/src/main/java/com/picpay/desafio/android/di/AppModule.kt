package com.picpay.desafio.android.di

import com.picpay.desafio.android.data.UserDataSource
import com.picpay.desafio.android.data.UserRepository
import com.picpay.desafio.android.data.local.LocalUserDataSource
import com.picpay.desafio.android.data.remote.RemoteUserDataSource
import com.picpay.desafio.android.domain.usecase.GetUsersUseCase
import com.picpay.desafio.android.domain.usecase.GetUsersUseCaseImpl
import com.picpay.desafio.android.presentation.userlist.UserListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    single<GetUsersUseCase> { GetUsersUseCaseImpl(get()) }

    single<UserDataSource>(named("remote")) { RemoteUserDataSource() }

    single<UserDataSource>(named("local")) { LocalUserDataSource(get()) }

    single { UserRepository(get(named("remote")), get(named("local")), get()) }

    viewModel { UserListViewModel(get()) }
}