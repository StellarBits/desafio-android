package com.picpay.desafio.android

import android.app.Application
import com.picpay.desafio.android.data.local.localModule
import com.picpay.desafio.android.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Start the library
        startKoin {
            androidContext(this@AppApplication)

            // AppModule.kt reference
            modules(localModule, appModule)
        }
    }
}