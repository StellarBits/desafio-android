package com.picpay.desafio.android.data.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.picpay.desafio.android.domain.model.User
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/"

/**
 * Endpoints.
 */
interface PicPayService {

    @GET("users")
    fun getUsers(): Call<List<User>>
}

/**
 * Retrofit singleton.
 */
object PicPayApi {
    private val gson: Gson by lazy { GsonBuilder().create() }

    private val okHttp: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val service: PicPayService by lazy {
        retrofit.create(PicPayService::class.java)
    }
}