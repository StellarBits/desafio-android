package com.picpay.desafio.android.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.picpay.desafio.android.domain.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getUsers(): Flow<List<User>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg user: User)

    @Query("DELETE FROM user")
    suspend fun deleteUsers()
}