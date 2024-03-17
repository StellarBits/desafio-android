package com.picpay.desafio.android.data.remote

import com.picpay.desafio.android.data.UserDataSource
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.util.SUCCESS
import com.picpay.desafio.android.util.UNKNOWN_ERROR
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.suspendCoroutine

class RemoteUserDataSource : UserDataSource {
    override suspend fun getUsers(): Pair<List<User>, String> {

        return suspendCoroutine { continuation ->

            PicPayApi.service.getUsers()
                .enqueue(object : Callback<List<User>> {
                    override fun onFailure(call: Call<List<User>>, t: Throwable) {
                        continuation.resumeWith(Result.success(Pair(listOf(), t.message ?: UNKNOWN_ERROR)))
                    }

                    override fun onResponse(
                        call: Call<List<User>>,
                        response: Response<List<User>>
                    ) {
                        if (response.isSuccessful) {
                            val users = response.body() ?: listOf()

                            if (users.isNotEmpty()) {
                                continuation.resumeWith(Result.success(Pair(users, SUCCESS)))
                            } else {
                                continuation.resumeWith(Result.success(Pair(listOf(), SUCCESS)))
                            }
                        } else {
                            val errorMessage = response.errorBody()?.string() ?: UNKNOWN_ERROR
                            continuation.resumeWith(Result.success(Pair(listOf(), errorMessage)))
                        }
                    }
                })
        }
    }
}