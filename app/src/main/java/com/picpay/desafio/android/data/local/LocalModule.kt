package com.picpay.desafio.android.data.local

import org.koin.dsl.module

val localModule = module {

    single { AppDatabase.getDatabase(get()) }

    single { get<AppDatabase>().userDao() }
}
